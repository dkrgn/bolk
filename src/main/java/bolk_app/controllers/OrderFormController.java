package bolk_app.controllers;

import bolk_app.dto.OrderResponse;
import bolk_app.dto.UnitWrapper;
import bolk_app.models.Order;
import bolk_app.models.Status;
import bolk_app.models.Unit;
import bolk_app.repositories.CustomerRepo;
import bolk_app.repositories.UnitRepo;
import bolk_app.repositories.OrderRepo;
import bolk_app.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders-form.html/")
public class OrderFormController {

    private UnitRepo unitRepo;
    private OrderRepo orderRepo;
    private CustomerRepo customerRepo;

    @Autowired
    public OrderFormController(UnitRepo unitRepo, OrderRepo orderRepo, CustomerRepo customerRepo) {
        this.unitRepo = unitRepo;
        this.orderRepo = orderRepo;
        this.customerRepo = customerRepo;
    }


    @GetMapping("{id}")
    public OrderResponse getOrder(
            @PathVariable("id") String id) {
        Order order = orderRepo.getOrderById(Integer.parseInt(id));
        System.err.println("METHOD FIRED");
        return OrderService.getFormResponse(order, customerRepo);
    }

    @PostMapping(value = "save/{id}",
                 produces = MediaType.APPLICATION_JSON_VALUE,
                 consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<Unit> saveGoods(
            @PathVariable("id") String id,
            @RequestBody UnitWrapper data) {
        for (Unit u : data.getUnits()) {
            Unit unit = new Unit();
            unit.setDeliveryCompany(u.getDeliveryCompany());
            unit.setFragile(u.isFragile());
            unit.setHeight(u.getHeight());
            unit.setLength(u.getLength());
            unit.setSealed(u.isSealed());
            unit.setType(u.getType());
            unit.setWeight(u.getWeight());
            unit.setWidth(u.getWidth());
            unit.setOrder(orderRepo.getOrderById(Integer.parseInt(id)));
            unitRepo.save(unit);
            System.out.println(Status.FINISHED.name() + "      " + Integer.parseInt(id));
            orderRepo.changeOrderStatus(Status.FINISHED.name(), Integer.parseInt(id));
        }
        return unitRepo.getUnitByOrderIdLimited(Integer.parseInt(id), data.getUnits().size());
    }

}
