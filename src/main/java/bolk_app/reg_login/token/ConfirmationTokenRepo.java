package bolk_app.reg_login.token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConfirmationTokenRepo extends JpaRepository<ConfirmationToken, Long> {

    @Query(value = "SELECT * FROM confirmation_tokens WHERE token = :token", nativeQuery = true)
    ConfirmationToken findByToken(String token);

    @Query(value = "SELECT * FROM confirmation_tokens WHERE NOW() >= expires_at", nativeQuery = true)
    List<ConfirmationToken> clearExpiredTokens();
}
