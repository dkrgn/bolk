package bolk_app.reg_login.dto;

import lombok.Data;

/**
 * Class to build a Login request to get from frontend
 */
@Data
public class LoginRequest {

    private String email;
    private String password;
}
