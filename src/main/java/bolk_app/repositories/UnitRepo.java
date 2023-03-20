package bolk_app.repositories;

import bolk_app.models.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnitRepo extends JpaRepository<Unit, Integer> {

}
