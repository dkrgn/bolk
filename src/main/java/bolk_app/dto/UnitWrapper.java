package bolk_app.dto;

import bolk_app.models.Unit;
import lombok.Data;

import java.util.List;

/**
 * Class to receive list of Unit object from frontend request
 */
@Data
public class UnitWrapper {

    List<Unit> units;
}
