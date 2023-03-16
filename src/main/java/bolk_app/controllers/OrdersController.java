package bolk_app.controllers;

import bolk_app.models.Order;
import bolk_app.models.Status;
import bolk_app.repositories.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrdersController {

    private OrderRepo orderRepo;

    @Autowired
    public OrdersController(OrderRepo orderRepo) {
        this.orderRepo = orderRepo;
    }

    @GetMapping("/all")
    public List<Order> getAllOrders() {
        return orderRepo.getAll();
    }

    @GetMapping("/pending")
    public List<Order> getPendingOrders() {
        return orderRepo.getOrderByStatus(Status.PENDING);
    }

    @GetMapping("/finished")
    public List<Order> getFinishedOrders() {
        return orderRepo.getOrderByStatus(Status.FINISHED);
    }



}
