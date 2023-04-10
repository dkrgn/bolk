package bolk_app.services;

import bolk_app.dto.UnitWrapper;
import bolk_app.models.Status;
import bolk_app.models.Unit;
import bolk_app.repositories.OrderRepo;
import bolk_app.repositories.UnitRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class FormService {

    private final OrderRepo orderRepo;

    private final UnitRepo unitRepo;

    private final XMLBuilderService xmlBuilderService;

    private final JSONBuilderService jsonBuilderService;

    public List<Unit> saveUnitAndReturnResults(int id, UnitWrapper data) {
        List<Unit> units = new ArrayList<>();
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
            unit.setOrder(orderRepo.getOrderById(id));
            unitRepo.save(unit);
            units.add(unit);
            orderRepo.changeOrderStatus(Status.FINISHED.name(), id);
        }
        xmlBuilderService.build(orderRepo.getOrderById(id), units);
        jsonBuilderService.build(orderRepo.getOrderById(id), units);
        return unitRepo.getUnitByOrderIdLimited(id, data.getUnits().size());
    }
}
