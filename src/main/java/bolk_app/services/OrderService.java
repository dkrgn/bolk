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

/**
 * Service class to retrieve Order objects from database, check tokens and delete an order
 */
@Service
@AllArgsConstructor
public class OrderService {

    private final CustomerRepo customerRepo;

    private final OrderRepo orderRepo;

    private final ConfirmationTokenService tokenService;

    /**
     * Method to get Order response object by requested id
     * @param id of Order object requested
     * @return Order response object
     */
    public OrderResponse getResponse(int id) {
        Order order = orderRepo.getOrderById(id);
        return new OrderResponse(order.getId(),
                order.getDate(),
                customerRepo.getNameByOrderId(order.getId()),
                order.getStatus(),
                order.getNrPallets());
    }

    /**
     * Method to get all Order objects from database
     * @return list of Order response objects
     */
    public List<OrderResponse> getAll() {
        List<Order> orders = orderRepo.getAll();
        return collect(orders);
    }

    /**
     * Method to get list of Order responses objects by requested status
     * @param status requested
     * @return list of Order response objects
     */
    public List<OrderResponse> getResponsesByStatus(Status status) {
        List<Order> orders = orderRepo.getOrdersByStatus(status.name());
        return collect(orders);
    }

    /**
     * Method to transform Order object in DTO Order response object
     * @param orders to transform
     * @return list of Order response objects
     */
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

    /**
     * Method to check if User's token is valid
     * @param tokenRequested: token from request to check
     * @return true/false depending on if the token is valid or not
     */
    public boolean isTokenValid(String tokenRequested) {
        clearExpiredTokens();
        ConfirmationToken token = tokenService.getToken(tokenRequested);
        if (token != null) {
            return LocalDateTime.now().isBefore(token.getExpiresAt());
        }
        return false;
    }

    /**
     * Method to delete expired tokens
     */
    public void clearExpiredTokens() {
        tokenService.clearExpiredTokens();
    }

    /**
     * Method to delete Order object by its id
     * @param id of Order requested
     * @return id of deleted Order object
     */
    public String deleteOrder(String id) {
        Order order = orderRepo.getOrderById(Integer.parseInt(id));
        orderRepo.delete(order);
        return id;
    }
}
