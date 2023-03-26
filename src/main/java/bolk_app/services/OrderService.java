package bolk_app.services;

import bolk_app.dto.OrderResponse;
import bolk_app.models.Order;
import bolk_app.repositories.CustomerRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    public static OrderResponse getResponse(Order order, CustomerRepo customerRepo) {
        return new OrderResponse(order.getId(), order.getDate(), customerRepo.getNameByOrderId(order.getId()), order.getStatus(), 0);
    }

    public static List<OrderResponse> getResponses(List<Order> orders, CustomerRepo customerRepo) {
        List<OrderResponse> responses = new ArrayList<>();
        for (Order order : orders) {
            responses.add(new OrderResponse(order.getId(), order.getDate(), customerRepo.getNameByOrderId(order.getId()), order.getStatus(), 0));
        }
        return responses.stream()
                .sorted((o1, o2) -> Integer.compare(o1.getId(), o2.getId()))
                .collect(Collectors.toList());
    }

    public static OrderResponse getFormResponse(Order order, CustomerRepo customerRepo) {
        return new OrderResponse(order.getId(), order.getDate(), customerRepo.getNameByOrderId(order.getId()), order.getStatus(), order.getNrPallets());
    }
}
