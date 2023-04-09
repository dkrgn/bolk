package bolk_app.controllers;

import bolk_app.dto.UnitRequest;
import bolk_app.dto.UnitResponse;
import bolk_app.models.Unit;
import bolk_app.services.UnitService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("/edit-unit.html/")
public class EditUnitController {

    private final UnitService unitService;

    @GetMapping("{id}")
    public UnitResponse getUnit(
            @Valid @PathVariable("id") String id
    ) {
        System.err.println("id " + id);
        return unitService.getUnitById(Integer.parseInt(id));
    }

    @PutMapping("update/{id}")
    @ResponseBody
    public Unit updateUnit(
            @Valid @PathVariable("id") String id,
            @RequestBody UnitRequest request
            ) {
        return unitService.updateUnitById(Integer.parseInt(id), request);
    }
}
