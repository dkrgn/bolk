package bolk_app.controllers;

import bolk_app.dto.OrderResponse;
import bolk_app.models.Status;
import bolk_app.services.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class OrdersListController {

    private final OrderService orderService;

    @GetMapping("/check")
    @ResponseBody
    public boolean isTokenValid(
            @RequestParam("token") String token
    ) {
        return orderService.isTokenValid(token);
    }

    @GetMapping("/all")
    public List<OrderResponse> getAllOrders() {
        return orderService.getAll();
    }

    @GetMapping("/pending")
    public List<OrderResponse> getPendingOrders() {
        return orderService.getResponsesByStatus(Status.PENDING);
    }

    @GetMapping("/finished")
    public List<OrderResponse> getFinishedOrders() {
        return orderService.getResponsesByStatus(Status.FINISHED);
    }

    @GetMapping("/search/{id}")
    public OrderResponse getOrdersBySearch(
            @PathVariable("id") String id) {
        return orderService.getResponse(Integer.parseInt(id));
    }

    @DeleteMapping("/delete/{id}")
    public String deleteOrder(
            @PathVariable("id") String id
    ) {
        return orderService.deleteOrder(id);
    }
}
