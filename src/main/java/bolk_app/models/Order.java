package bolk_app.models;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Set;

@Data
@Entity
@Table(name = "orders") //goederenregel
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(columnDefinition = "DATE")
    private LocalDate date;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(columnDefinition = "VARCHAR(255)")
    @NotNull
    private String description;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false, referencedColumnName = "id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "recipient_id", nullable = false, referencedColumnName = "id")
    private Recipient recipient;

    @OneToMany(mappedBy = "order")
    private Set<Goods> goodsSet;
}
