package bolk_app.repositories;

import bolk_app.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Integer> {

    @Query(value = "SELECT name FROM customers WHERE id = :id", nativeQuery = true)
    String getNameByOrderId(int id);
}
