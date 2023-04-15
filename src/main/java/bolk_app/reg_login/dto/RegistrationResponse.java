package bolk_app.reg_login.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Class to build a Registration response to send to frontend
 */
@Data
@AllArgsConstructor
public class RegistrationResponse {
    private String email;
}
