package bolk_app.controllers;

import bolk_app.dto.OrderResponse;
import bolk_app.dto.UnitResponse;
import bolk_app.services.OrderService;
import bolk_app.services.UnitService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class responsible to process API requests from /admin-form.html/
 */
@RestController
@RequestMapping("/admin-form.html/")
@AllArgsConstructor
public class AdminFormController {

    private final OrderService orderService;

    private UnitService unitService;

    /**
     * Method to return a response object of Order
     * @param id of Order comes with a request
     * @return response with Order information by specified id
     */
    @GetMapping("{id}")
    public OrderResponse getOrder(
            @PathVariable("id") String id) {
        return orderService.getResponse(Integer.parseInt(id));
    }

    /**
     * Method to return a list of Unit responses filled with Units data contained in specific Order
     * @param id of Order comes with a request
     * @return list of units' responses depending on Order id
     */
    @GetMapping("units/{id}")
    public List<UnitResponse> getUnits(
            @PathVariable("id") String id) {
        return unitService.getUnitsByOrderId(Integer.parseInt(id));
    }

    /**
     * Method to delete specific unit by its id
     * @param id of Unit to delete
     * @return id of deleted unit meaning deletion went successfully
     */
    @DeleteMapping("delete/{id}")
    public int deleteUnit(
            @PathVariable("id") String id
    ) {
        return unitService.deleteUnit(Integer.parseInt(id));
    }
}
