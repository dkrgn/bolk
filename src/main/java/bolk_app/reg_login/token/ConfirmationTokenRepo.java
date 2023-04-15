package bolk_app.reg_login.token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository class to connect to database and manage ConfirmationToken objects in it
 */
@Repository
public interface ConfirmationTokenRepo extends JpaRepository<ConfirmationToken, Long> {

    /**
     * Method (query) to find an existing ConfirmationToken object in the database by its token parameter
     * @param token to use in a query to get particular ConfirmationToken object
     * @return ConfirmationToken object
     */
    @Query(value = "SELECT * FROM confirmation_tokens WHERE token = :token", nativeQuery = true)
    ConfirmationToken findByToken(String token);

    /**
     * Method to return a list of ConfirmationToken object which expired
     * @return a list of ConfirmationToken object
     */
    @Query(value = "SELECT * FROM confirmation_tokens WHERE NOW() >= expires_at", nativeQuery = true)
    List<ConfirmationToken> clearExpiredTokens();
}
