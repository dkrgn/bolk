package bolk_app.repositories;

import bolk_app.models.Recipient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Repository class to connect to database and manage Recipient objects in it
 */
@Repository
public interface RecipientRepo extends JpaRepository<Recipient, Integer> {

    /**
     * Method to return a Recipient object by requested id
     * @param orderRecipientId: requested Recipient object id
     * @return Recipient object
     */
    @Query(value = "SELECT * FROM recipients WHERE id = :orderRecipientId", nativeQuery = true)
    Recipient getRecipientByOrderId(int orderRecipientId);
}
