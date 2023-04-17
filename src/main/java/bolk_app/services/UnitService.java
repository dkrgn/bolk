package bolk_app.services;

import bolk_app.dto.UnitRequest;
import bolk_app.dto.UnitResponse;
import bolk_app.models.Unit;
import bolk_app.models.UnitType;
import bolk_app.repositories.UnitRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class to retrieve Unit objects and update them
 */
@Service
@AllArgsConstructor
public class UnitService {

    private final UnitRepo unitRepo;

    private final XMLBuilderService xmlBuilderService;

    private final JSONBuilderService jsonBuilderService;

    /**
     * Method to return list of Unit objects by Order id
     * @param id of Order requested
     * @return list of Unit response objects
     */
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

    /**
     * Method to delete Unit object by its id
     * @param id requested
     * @return id of deleted Unit
     */
    public int deleteUnit(int id) {
        Unit unit = unitRepo.getUnitById(id);
        unitRepo.delete(unit);
        xmlBuilderService.build(unit.getOrder(), unitRepo.getUnitsByOrderId(unit.getOrder().getId()));
        jsonBuilderService.build(unit.getOrder(), unitRepo.getUnitsByOrderId(unit.getOrder().getId()));
        return id;
    }

    /**
     * Method to update existing Unit object by its id with new provided data
     * @param id of Unit
     * @param request: data with which existing Unit should be updated
     * @return updated Unit
     */
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
        xmlBuilderService.build(u.getOrder(), unitRepo.getUnitsByOrderId(u.getOrder().getId()));
        jsonBuilderService.build(u.getOrder(), unitRepo.getUnitsByOrderId(u.getOrder().getId()));
        return u;
    }

    /**
     * Method to transform Unit object into DTO Unit response object
     * @param u: unit to be transformed
     * @return Unit response object
     */
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

    /**
     * Method to get Unit response object by its id
     * @param id requested
     * @return Unit response object
     */
    public UnitResponse getUnitById(int id) {
        Unit u = unitRepo.getUnitById(id);
        return buildResponse(u);
    }
}
