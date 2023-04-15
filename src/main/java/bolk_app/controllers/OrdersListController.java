package bolk_app.controllers;

import bolk_app.dto.OrderResponse;
import bolk_app.models.Status;
import bolk_app.services.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class responsible to process API requests from localhost:8080/
 */
@RestController
@AllArgsConstructor
public class OrdersListController {

    private final OrderService orderService;

    /**
     * Method to check is User's token has not expired
     * @param token: User's current token
     * @return depending on if it has expired or not returns true/fasle
     */
    @GetMapping("/check")
    @ResponseBody
    public boolean isTokenValid(
            @RequestParam("token") String token
    ) {
        return orderService.isTokenValid(token);
    }

    /**
     * Method return a list of all existing Order objects from database
     * @return list of Order objects
     */
    @GetMapping("/all")
    public List<OrderResponse> getAllOrders() {
        return orderService.getAll();
    }

    /**
     * Method returns a list of Order objects with status PENDING
     * @return list of Order objects with status PENDING
     */
    @GetMapping("/pending")
    public List<OrderResponse> getPendingOrders() {
        return orderService.getResponsesByStatus(Status.PENDING);
    }

    /**
     * Method returns a list of Order objects with status FINISHED
     * @return list of Order objects with status FINISHED
     */
    @GetMapping("/finished")
    public List<OrderResponse> getFinishedOrders() {
        return orderService.getResponsesByStatus(Status.FINISHED);
    }

    /**
     * Method returns an Order object by requested id
     * @param id of an Order object
     * @return Order response object
     */
    @GetMapping("/search/{id}")
    public OrderResponse getOrdersBySearch(
            @PathVariable("id") String id) {
        return orderService.getResponse(Integer.parseInt(id));
    }

    /**
     * Method deletes an Order object from database by requested id
     * @param id of an Order object
     * @return id of deleted Order object
     */
    @DeleteMapping("/delete/{id}")
    public String deleteOrder(
            @PathVariable("id") String id
    ) {
        return orderService.deleteOrder(id);
    }
}
