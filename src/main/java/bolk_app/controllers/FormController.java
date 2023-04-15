package bolk_app.controllers;

import bolk_app.dto.OrderResponse;
import bolk_app.dto.UnitWrapper;
import bolk_app.models.Unit;
import bolk_app.services.FormService;
import bolk_app.services.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class responsible to process API requests from /orders-form.html/
 */
@RestController
@RequestMapping("/orders-form.html/")
@AllArgsConstructor
public class FormController {

    private final OrderService orderService;

    private final FormService formService;

    /**
     * Method to return Order response object by requested id
     * @param id of Order from request
     * @return Order response object
     */
    @GetMapping("{id}")
    public OrderResponse getOrder(
            @PathVariable("id") String id) {
        return orderService.getResponse(Integer.parseInt(id));
    }

    /**
     * Method to save a list of Unit objects for specific Order into database by request
     * @param id of Order object for which Unit object will be saved
     * @param data: list of Unit objects to save
     * @return a list of saved Units
     */
    @PostMapping(value = "save/{id}",
                 produces = MediaType.APPLICATION_JSON_VALUE,
                 consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<Unit> saveGoods(
            @PathVariable("id") String id,
            @RequestBody UnitWrapper data) {
        return formService.saveUnitAndReturnResults(Integer.parseInt(id), data);
    }

}
