package bolk_app.reg_login.token;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ConfirmationTokenService {

    private final ConfirmationTokenRepo tokenRepo;

    public void saveConfirmationToken(ConfirmationToken token) {
        tokenRepo.save(token);
    }

    public ConfirmationToken getToken(String token) {
        return tokenRepo.findByToken(token);
    }

    public void clearExpiredTokens() {
        List<ConfirmationToken> tokens = tokenRepo.clearExpiredTokens();
        tokenRepo.deleteAll(tokens);
    }
}
