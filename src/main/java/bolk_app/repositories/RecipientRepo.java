package bolk_app.repositories;

import bolk_app.models.Recipient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecipientRepo extends JpaRepository<Recipient, Integer> {

    @Query(value = "SELECT * FROM recipients WHERE id = :orderRecipientId", nativeQuery = true)
    Recipient getRecipientByOrderId(int orderRecipientId);
}
