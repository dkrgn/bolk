package bolk_app.repositories;

import bolk_app.models.Order;
import bolk_app.models.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface OrderRepo extends JpaRepository<Order, Integer> {

    @Query(value = "SELECT * FROM orders WHERE status = :status", nativeQuery = true)
    List<Order> getOrdersByStatus(String status);

    @Query(value = "SELECT * FROM orders", nativeQuery = true)
    List<Order> getAll();

    @Query(value = "SELECT * FROM orders WHERE id = :id", nativeQuery = true)
    Order getOrderById(int id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE orders SET status = :status WHERE id = :id", nativeQuery = true)
    void changeOrderStatus(String status, int id);
}

