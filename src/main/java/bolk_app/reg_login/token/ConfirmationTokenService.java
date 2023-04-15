package bolk_app.reg_login.token;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class to work with ConformationToken objects
 */
@Service
@AllArgsConstructor
public class ConfirmationTokenService {

    private final ConfirmationTokenRepo tokenRepo;

    /**
     * Method to save a ConfirmationToken object into a database
     * @param token value from request
     */
    public void saveConfirmationToken(ConfirmationToken token) {
        tokenRepo.save(token);
    }

    /**
     * Method to receive a ConfirmationToken object by token parameter
     * @param token: requested token value
     * @return ConfirmationToken object
     */
    public ConfirmationToken getToken(String token) {
        return tokenRepo.findByToken(token);
    }

    /**
     * Method to delete ConfirmationToken object from database if their token has expired
     */
    public void clearExpiredTokens() {
        List<ConfirmationToken> tokens = tokenRepo.clearExpiredTokens();
        tokenRepo.deleteAll(tokens);
    }
}
