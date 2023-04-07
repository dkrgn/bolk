package bolk_app.reg_login.services;

import bolk_app.reg_login.User;
import bolk_app.reg_login.UserRepo;
import bolk_app.reg_login.security.PasswordEncoder;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepo userRepo;

    public String signUpUser(User user) {
        User isPresent = userRepo.findByEmail(user.getEmail());
        if(isPresent != null) {
            throw new IllegalStateException("Email already taken");
        }
        String passwordEncoded = PasswordEncoder.getPasswordEncoded(user.getPassword());
        user.setPassword(passwordEncoded);
        userRepo.save(user);
        return user.getEmail();
    }

}
