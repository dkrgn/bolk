package bolk_app.reg_login.controllers;

import bolk_app.reg_login.dto.LoginRequest;
import bolk_app.reg_login.dto.LoginResponse;
import bolk_app.reg_login.services.LoginService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Controller class responsible to process API requests from /login.html/
 */
@RestController
@RequestMapping("/login.html")
@AllArgsConstructor
public class LoginController {

    private final LoginService loginService;

    /**
     * Method to authenticate a User when trying to log in
     * @param request with User's data, namely email and password, checks if such user exists and either returns
     * token and role of user or return bad request
     * @return response entity either with token and role or bad request
     */
    @PostMapping(
            value = "/check",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public ResponseEntity<LoginResponse> checkCredentials(
            @Valid @RequestBody LoginRequest request
            ) {
        System.err.println("fired");
        return ResponseEntity.ok(loginService.checkCredentials(request));
    }
}
