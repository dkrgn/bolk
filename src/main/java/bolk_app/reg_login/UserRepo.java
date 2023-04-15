package bolk_app.reg_login;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Repository class to connect to database and manage User objects in it
 */
@Repository
public interface UserRepo extends JpaRepository<User, Integer> {

    /**
     * Method to find User by its email provided in request
     * @param email: user's email from request
     * @return User object
     */
    @Query(value = "SELECT * FROM users WHERE email = :email", nativeQuery = true)
    User findByEmail(String email);

}
