package bolk_app.reg_login.controllers;

import bolk_app.reg_login.dto.RegistrationRequest;
import bolk_app.reg_login.dto.RegistrationResponse;
import bolk_app.reg_login.services.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Controller class responsible to process API requests from /register
 */
@RestController
@RequestMapping(path = "/register")
@AllArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    /**
     * Method to register a new user with role 'EMPLOYEE'. Check if such user already registered and returns either newly
     * registered email or bad request
     * @param request User data from frontend request
     * @return email or bad request
     */
    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public ResponseEntity<RegistrationResponse> registerUser(
            @Valid @RequestBody RegistrationRequest request
    ) {
        return ResponseEntity.ok(new RegistrationResponse(registrationService.register(request)));
    }
}
