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

@RestController
@RequestMapping("/orders-form.html/")
@AllArgsConstructor
public class FormController {

    private final OrderService orderService;

    private final FormService formService;

    @GetMapping("{id}")
    public OrderResponse getOrder(
            @PathVariable("id") String id) {
        return orderService.getResponse(Integer.parseInt(id));
    }

    @PostMapping(value = "save/{id}",
                 produces = MediaType.APPLICATION_JSON_VALUE,
                 consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<Unit> saveGoods(
            @PathVariable("id") String id,
            @RequestBody UnitWrapper data) {
        return formService.saveUnitAndReturnResults(Integer.parseInt(id), data);
    }

}
