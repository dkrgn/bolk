package bolk_app.reg_login.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Class to build a Login response to send to frontend
 */
@Data
@AllArgsConstructor
public class LoginResponse {

    private String token;
    private String role;
}
