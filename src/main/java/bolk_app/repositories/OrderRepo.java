package bolk_app.repositories;

import bolk_app.models.Order;
import bolk_app.models.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepo extends JpaRepository<Order, Integer> {

    @Query(value = "SELECT * FROM orders WHERE status = :status", nativeQuery = true)
    public List<Order> getOrderByStatus(Status status);

    @Query(value = "SELECT * FROM orders", nativeQuery = true)
    public List<Order> getAll();
}
