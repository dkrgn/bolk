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
        return OrderService.getResponses(orders, customerRepo);
    }

    @GetMapping("/pending")
    public List<OrderResponse> getPendingOrders() {
        List<Order> pending = orderRepo.getOrdersByStatus(Status.PENDING.toString());
        return OrderService.getResponses(pending, customerRepo);
    }

    @GetMapping("/finished")
    public List<OrderResponse> getFinishedOrders() {
        List<Order> finished = orderRepo.getOrdersByStatus(Status.FINISHED.toString());
        return OrderService.getResponses(finished, customerRepo);
    }

    @GetMapping("/search/{id}")
    public OrderResponse getOrdersBySearch(
            @PathVariable("id") String id) {
        Order order = orderRepo.getOrderById(Integer.parseInt(id));
        return OrderService.getResponse(order, customerRepo);
    }

    @GetMapping("/OrderForm.html/{id}")
    public OrderResponse getOrderForForm(
            @PathVariable("id") String id) {
        Order order = orderRepo.getOrderById(Integer.parseInt(id));
        return OrderService.getResponse(order, customerRepo);
    }

}
