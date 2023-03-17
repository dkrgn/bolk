package bolk_app.dto;

import bolk_app.models.Status;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class OrderResponse {

    private int id;
    private LocalDate date;
    private String customerName;
    private Status status;

}
