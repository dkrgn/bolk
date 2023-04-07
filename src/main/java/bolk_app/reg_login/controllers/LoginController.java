package bolk_app.reg_login.controllers;

import bolk_app.reg_login.dto.LoginRequest;
import bolk_app.reg_login.dto.LoginResponse;
import bolk_app.reg_login.services.LoginService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/login.html")
@AllArgsConstructor
public class LoginController {

    private final LoginService loginService;

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
