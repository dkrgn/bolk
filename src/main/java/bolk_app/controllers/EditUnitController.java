package bolk_app.controllers;

import bolk_app.dto.UnitRequest;
import bolk_app.dto.UnitResponse;
import bolk_app.models.Unit;
import bolk_app.services.UnitService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Controller class responsible to process API requests from /edit-unit.html/
 */
@RestController
@AllArgsConstructor
@RequestMapping("/edit-unit.html/")
public class EditUnitController {

    private final UnitService unitService;

    /**
     * Method to return a Unit response object depending on its from request id
     * @param id of unit requested
     * @return Unit response object
     */
    @GetMapping("{id}")
    public UnitResponse getUnit(
            @Valid @PathVariable("id") String id
    ) {
        return unitService.getUnitById(Integer.parseInt(id));
    }

    /**
     * Method to update exisitng unit with new data by request
     * @param id of Unit to be updated
     * @param request new object of Unit request with new data for update
     * @return a updated Unit object
     */
    @PutMapping("update/{id}")
    @ResponseBody
    public Unit updateUnit(
            @Valid @PathVariable("id") String id,
            @RequestBody UnitRequest request
            ) {
        return unitService.updateUnitById(Integer.parseInt(id), request);
    }
}
