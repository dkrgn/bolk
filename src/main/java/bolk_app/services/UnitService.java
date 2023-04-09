package bolk_app.services;

import bolk_app.dto.UnitRequest;
import bolk_app.dto.UnitResponse;
import bolk_app.models.Unit;
import bolk_app.models.UnitType;
import bolk_app.repositories.UnitRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EnumType;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UnitService {

    private final UnitRepo unitRepo;

    public List<UnitResponse> getUnitsByOrderId(int id) {
        List<Unit> units = unitRepo.getUnitsByOrderId(id);
        List<UnitResponse> responses = new ArrayList<>();
        for (Unit u : units) {
            responses.add(buildResponse(u));
        }
        return responses.stream()
                .sorted((o1, o2) -> Integer.compare(o1.getId(), o2.getId()))
                .collect(Collectors.toList());
    }

    public int deleteUnit(int id) {
        Unit unit = unitRepo.getUnitById(id);
        unitRepo.delete(unit);
        return id;
    }

    public Unit updateUnitById(int id, UnitRequest request) {
        Unit u = unitRepo.getUnitById(id);
        u.setDeliveryCompany(request.getDeliveryCompany());
        u.setType(Enum.valueOf(UnitType.class, request.getType()));
        u.setLength(request.getLength());
        u.setHeight(request.getHeight());
        u.setWidth(request.getWidth());
        u.setWeight(request.getWeight());
        u.setSealed(request.isSealed());
        u.setFragile(request.isFragile());
        unitRepo.save(u);
        return u;
    }

    public UnitResponse buildResponse(Unit u) {
        UnitResponse response = new UnitResponse();
        response.setId(u.getId());
        response.setDeliveryCompany(u.getDeliveryCompany());
        response.setFragile(u.isFragile());
        response.setHeight(u.getHeight());
        response.setLength(u.getLength());
        response.setSealed(u.isSealed());
        response.setType(u.getType().name());
        response.setWeight(u.getWeight());
        response.setWidth(u.getWidth());
        return response;
    }

    public UnitResponse getUnitById(int id) {
        Unit u = unitRepo.getUnitById(id);
        return buildResponse(u);
    }
}
