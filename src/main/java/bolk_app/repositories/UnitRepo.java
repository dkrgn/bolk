package bolk_app.repositories;

import bolk_app.models.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository class to connect to database and manage Unit objects in it
 */
@Repository
public interface UnitRepo extends JpaRepository<Unit, Integer> {

    /**
     * Method to return Unit objects associated with particular Order object
     * @param id of an Order object
     * @param limit: the amount of Unit objects associated with an Order object
     * @return list of Unit objects
     */
    @Query(value = "SELECT * FROM units WHERE order_id = :id ORDER BY id DESC LIMIT :limit", nativeQuery = true)
    List<Unit> getUnitByOrderIdLimited(int id, int limit);

    /**
     * Method to get Unit objects associated with particular Order object id
     * @param id Order object's id
     * @return list of Unit objects associated with particular Order object
     */
    @Query(value = "SELECT * FROM units WHERE order_id = :id", nativeQuery = true)
    List<Unit> getUnitsByOrderId(int id);

    /**
     * Method to get Unit object by its requested id
     * @param id Unit object id from request
     * @return Unit object
     */
    @Query(value = "SELECT * FROM units WHERE id = :id", nativeQuery = true)
    Unit getUnitById(int id);
}
