package bolk_app.controllers;

import bolk_app.dto.OrderResponse;
import bolk_app.dto.UnitResponse;
import bolk_app.services.OrderService;
import bolk_app.services.UnitService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin-form.html/")
@AllArgsConstructor
public class AdminFormController {

    private final OrderService orderService;

    private UnitService unitService;

    @GetMapping("{id}")
    public OrderResponse getOrder(
            @PathVariable("id") String id) {
        return orderService.getResponse(Integer.parseInt(id));
    }

    @GetMapping("units/{id}")
    public List<UnitResponse> getUnits(
            @PathVariable("id") String id) {
        return unitService.getUnitsByOrderId(Integer.parseInt(id));
    }

    @DeleteMapping("delete/{id}")
    public int deleteUnit(
            @PathVariable("id") String id
    ) {
        System.err.println("iddd" + id);
        return unitService.deleteUnit(Integer.parseInt(id));
    }
}
