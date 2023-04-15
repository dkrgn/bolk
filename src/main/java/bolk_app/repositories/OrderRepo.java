package bolk_app.repositories;

import bolk_app.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Repository class to connect to database and manage Order objects in it
 */
@Repository
public interface OrderRepo extends JpaRepository<Order, Integer> {

    /**
     * Method to return list of Order objects depending on requested status
     * @param status of order from request
     * @return list of Order object with requested status
     */
    @Query(value = "SELECT * FROM orders WHERE status = :status", nativeQuery = true)
    List<Order> getOrdersByStatus(String status);

    /**
     * Method to return list of all existing Order object
     * @return list of Order objects
     */
    @Query(value = "SELECT * FROM orders", nativeQuery = true)
    List<Order> getAll();

    /**
     * Method to return Order object depending on requested id
     * @param id requested
     * @return Order object with requested id
     */
    @Query(value = "SELECT * FROM orders WHERE id = :id", nativeQuery = true)
    Order getOrderById(int id);

    /**
     * Method to change status of an Order
     * @param status on which to change the current one in an Order object
     * @param id for which Order object to change status
     */
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE orders SET status = :status WHERE id = :id", nativeQuery = true)
    void changeOrderStatus(String status, int id);
}

