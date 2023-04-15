package bolk_app.reg_login.dto;

import lombok.Data;

/**
 * Class to build a Registration request to get from frontend
 */
@Data
public class RegistrationRequest {

    private final String name;
    private final String email;
    private final String password;
}
