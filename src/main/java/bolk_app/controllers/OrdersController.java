package bolk_app.controllers;

import bolk_app.dto.OrderResponse;
import bolk_app.models.Order;
import bolk_app.models.Status;
import bolk_app.repositories.CustomerRepo;
import bolk_app.repositories.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class OrdersController {

    private OrderRepo orderRepo;
    private CustomerRepo customerRepo;

    @Autowired
    public OrdersController(OrderRepo orderRepo, CustomerRepo customerRepo) {
        this.orderRepo = orderRepo;
        this.customerRepo = customerRepo;
    }

    @GetMapping("/all")
    public List<OrderResponse> getAllOrders() {
        List<Order> orders = orderRepo.getAll();
        List<OrderResponse> responses = new ArrayList<>();
        for (Order order : orders) {
            responses.add(new OrderResponse(order.getId(), order.getDate(), customerRepo.getNameByOrderId(order.getId()), order.getStatus()));
        }
        return responses;
    }

    @GetMapping("/pending")
    public List<Order> getPendingOrders() {
        return orderRepo.getOrderByStatus(Status.PENDING.toString());
    }

    @GetMapping("/finished")
    public List<Order> getFinishedOrders() {
        return orderRepo.getOrderByStatus(Status.FINISHED.toString());
    }



}
