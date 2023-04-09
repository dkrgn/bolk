package bolk_app.services;

import bolk_app.dto.OrderResponse;
import bolk_app.models.Order;
import bolk_app.models.Status;
import bolk_app.reg_login.token.ConfirmationToken;
import bolk_app.reg_login.token.ConfirmationTokenService;
import bolk_app.repositories.CustomerRepo;
import bolk_app.repositories.OrderRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderService {

    private final CustomerRepo customerRepo;

    private final OrderRepo orderRepo;

    private final ConfirmationTokenService tokenService;

    public OrderResponse getResponse(int id) {
        Order order = orderRepo.getOrderById(id);
        return new OrderResponse(order.getId(),
                order.getDate(),
                customerRepo.getNameByOrderId(order.getId()),
                order.getStatus(),
                order.getNrPallets());
    }

    public List<OrderResponse> getAll() {
        List<Order> orders = orderRepo.getAll();
        return collect(orders);
    }

    public List<OrderResponse> getResponsesByStatus(Status status) {
        List<Order> orders = orderRepo.getOrdersByStatus(status.name());
        return collect(orders);
    }

    public List<OrderResponse> collect(List<Order> orders) {
        List<OrderResponse> responses = new ArrayList<>();
        for (Order order : orders) {
            responses.add(new OrderResponse(order.getId(),
                    order.getDate(),
                    customerRepo.getNameByOrderId(order.getId()),
                    order.getStatus(),
                    0));
        }
        return responses.stream()
                .sorted((o1, o2) -> Integer.compare(o1.getId(), o2.getId()))
                .collect(Collectors.toList());
    }

    public boolean isTokenValid(String req) {
        clearExpiredTokens();
        ConfirmationToken token = tokenService.getToken(req);
        if (token != null) {
            return LocalDateTime.now().isBefore(token.getExpiresAt());
        }
        return false;
    }

    public void clearExpiredTokens() {
        tokenService.clearExpiredTokens();
    }

    public String deleteOrder(String id) {
        Order order = orderRepo.getOrderById(Integer.parseInt(id));
        orderRepo.delete(order);
        return id;
    }
}
