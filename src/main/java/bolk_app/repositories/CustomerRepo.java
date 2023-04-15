package bolk_app.repositories;

import bolk_app.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Repository class to connect to database and manage Customer objects in it
 */
@Repository
public interface CustomerRepo extends JpaRepository<Customer, Integer> {

    /**
     * Method to return name of Customer depending on provided Customer id
     * @param id: customer's id from request
     * @return name of Customer
     */
    @Query(value = "SELECT name FROM customers WHERE id = :id", nativeQuery = true)
    String getNameByOrderId(int id);
}
