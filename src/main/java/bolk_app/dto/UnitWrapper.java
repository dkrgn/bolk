package bolk_app.dto;

import bolk_app.models.Unit;
import lombok.Data;

import java.util.List;

@Data
public class UnitWrapper {

    List<Unit> units;
}
