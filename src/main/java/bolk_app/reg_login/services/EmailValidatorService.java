package bolk_app.reg_login.services;

import org.springframework.stereotype.Service;

import java.util.function.Predicate;

/**
 * Service class to check if provided email is valid
 */
@Service
public class EmailValidatorService implements Predicate<String> {

    @Override
    public boolean test(String s) {
        return s.matches("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$");
    }
}
