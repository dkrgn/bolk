package bolk_app.reg_login.services;

import bolk_app.reg_login.User;
import bolk_app.reg_login.UserRepo;
import bolk_app.reg_login.dto.LoginRequest;
import bolk_app.reg_login.dto.LoginResponse;
import bolk_app.reg_login.security.PasswordEncoder;
import bolk_app.reg_login.token.ConfirmationToken;
import bolk_app.reg_login.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class LoginService {

    private final UserRepo userRepo;
    private final ConfirmationTokenService tokenService;

    public LoginResponse checkCredentials(LoginRequest request) {
        System.err.println(request.toString());
        String email = request.getEmail();
        String password = request.getPassword();
        System.err.println(email + "     " + password);
        String isPresent = userRepo.findByEmail(email).getEmail();
        if (isPresent == null) {
            throw new IllegalStateException("wrong email");
        }
        User user = userRepo.findByEmail(email);
        String passwordEncoded = PasswordEncoder.getPasswordEncoded(password);
        if (!user.getPassword().equals(passwordEncoded)) {
            throw new IllegalStateException("wrong password");
        }
        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDate.now().atTime(22, 0),
                user
        );
        tokenService.saveConfirmationToken(confirmationToken);
        return new LoginResponse(token, user.getRole().name());
    }
}
