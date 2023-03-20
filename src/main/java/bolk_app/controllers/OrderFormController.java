package bolk_app.controllers;

import bolk_app.dto.OrderResponse;
import bolk_app.models.Unit;
import bolk_app.repositories.UnitRepo;
import bolk_app.repositories.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders-form.html")
public class OrderFormController {

    private UnitRepo unitRepo;
    private OrderRepo orderRepo;

    @Autowired
    public OrderFormController(UnitRepo unitRepo, OrderRepo orderRepo) {
        this.unitRepo = unitRepo;
        this.orderRepo = orderRepo;
    }


    @GetMapping("{id}")
    public List<OrderResponse> getOrder(
            @PathVariable("id") String id) {

        return null;
    }

    @PostMapping("{id}")
    public Unit saveGoods(
            @PathVariable("id") String order_id,
            @RequestParam("length") String length,
            @RequestParam("weight") String weight,
            @RequestParam("width") String width,
            @RequestParam("height") String height,
            @RequestParam("sealed") String sealed,
            @RequestParam("fragile") String fragile) {

        Unit unit = new Unit();
        unit.setLength(Float.parseFloat(length));
        unit.setWeight(Float.parseFloat(weight));
        unit.setWidth(Float.parseFloat(width));
        unit.setHeight(Float.parseFloat(height));
        unit.setSealed(Boolean.getBoolean(sealed));
        unit.setFragile(Boolean.getBoolean(fragile));
        unit.setOrder(orderRepo.getOrderById(Integer.parseInt(order_id)).get(0));
        unitRepo.save(unit);
        return unit;
    }

}
