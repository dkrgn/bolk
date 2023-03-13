package bolk_app.repositories;

import bolk_app.models.Recipient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipientRepo extends JpaRepository<Recipient, Integer> {
}
