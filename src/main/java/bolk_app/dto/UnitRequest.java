package bolk_app.dto;

import lombok.Data;

/**
 * Class to build a Unit request to get from frontend
 */
@Data
public class UnitRequest {

    private String deliveryCompany;
    private boolean fragile;
    private float height;
    private float length;
    private boolean sealed;
    private String type;
    private float weight;
    private float width;
}
