package bolk_app.controllers;

import bolk_app.dto.OrderResponse;
import bolk_app.models.Order;
import bolk_app.models.Status;
import bolk_app.repositories.CustomerRepo;
import bolk_app.repositories.OrderRepo;
import bolk_app.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

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
        return OrderService.getResponse(orders, customerRepo);
    }

    @GetMapping("/pending")
    public List<OrderResponse> getPendingOrders() {
        List<Order> pending = orderRepo.getOrderByStatus(Status.PENDING.toString());
        return OrderService.getResponse(pending, customerRepo);
    }

    @GetMapping("/finished")
    public List<OrderResponse> getFinishedOrders() {
        List<Order> finished = orderRepo.getOrderByStatus(Status.FINISHED.toString());
        return OrderService.getResponse(finished, customerRepo);
    }

    @GetMapping("/search/{id}")
    public List<OrderResponse> getOrdersBySearch(
            @PathVariable("id") String id) {
        List<Order> orders = orderRepo.getOrderById(Integer.parseInt(id));
        return OrderService.getResponse(orders, customerRepo);
    }

    @GetMapping("/OrderForm.html/{id}")
    public List<OrderResponse> getOrderForForm(
            @PathVariable("id") String id) {
        List<Order> orders = orderRepo.getOrderById(Integer.parseInt(id));
        return OrderService.getResponse(orders, customerRepo);
    }

}
