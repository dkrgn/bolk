package bolk_app.dto;

import lombok.Data;

/**
 * Class to build a Unit response to send to frontend
 */
@Data
public class UnitResponse {

    private int id;
    private String deliveryCompany;
    private boolean fragile;
    private float height;
    private float length;
    private boolean sealed;
    private String type;
    private float weight;
    private float width;
}
