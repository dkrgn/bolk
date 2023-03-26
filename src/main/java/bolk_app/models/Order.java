package bolk_app.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @Column(name = "artnr", columnDefinition = "VARCHAR(255)")
    private String artNr;

    @Column(columnDefinition = "VARCHAR(255)")
    @NotNull
    private String description;

    @Column(name = "nr_pallets")
    private int nrPallets;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false, referencedColumnName = "id")
    private Customer customer;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "recipient_id", nullable = false, referencedColumnName = "id")
    private Recipient recipient;

    @JsonIgnore
    @OneToMany(mappedBy = "order")
    private Set<Unit> unitSet;
}
