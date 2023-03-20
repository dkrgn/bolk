package bolk_app.services;

import bolk_app.dto.OrderResponse;
import bolk_app.models.Order;
import bolk_app.repositories.CustomerRepo;
import bolk_app.repositories.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    public static List<OrderResponse> getResponse(List<Order> orders, CustomerRepo customerRepo) {
        List<OrderResponse> responses = new ArrayList<>();
        for (Order order : orders) {
            responses.add(new OrderResponse(order.getId(), order.getDate(), customerRepo.getNameByOrderId(order.getId()), order.getStatus()));
        }
        return responses;
    }
}
