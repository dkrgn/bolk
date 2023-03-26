package bolk_app.repositories;

import bolk_app.models.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UnitRepo extends JpaRepository<Unit, Integer> {

    @Query(value = "SELECT * FROM units WHERE order_id = :id ORDER BY id DESC LIMIT :limit", nativeQuery = true)
    List<Unit> getUnitByOrderIdLimited(int id, int limit);

}
