package bolk_app.reg_login.services;

import bolk_app.reg_login.dto.RegistrationRequest;
import bolk_app.reg_login.Role;
import bolk_app.reg_login.User;
import lombok.Data;
import org.springframework.stereotype.Service;

@Service
@Data
public class RegistrationService {

    private final EmailValidatorService validator;
    private final UserService userService;

    public String register(RegistrationRequest request) {
        boolean isValidEmail = validator.test(request.getEmail());
        if (!isValidEmail) {
            throw new IllegalStateException("email not valid " + request.getEmail());
        }
        return userService.signUpUser(
                new User(
                        request.getName(),
                        request.getEmail(),
                        request.getPassword(),
                        Role.EMPLOYEE
                )
        );
    }
}
