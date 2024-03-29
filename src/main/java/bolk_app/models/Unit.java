package bolk_app.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * POJO Unit object to persist it in database
 */
@Setter
@Getter
@Entity
@Table(name = "units")
public class Unit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "delivery_company",
    columnDefinition = "VARCHAR(255)")
    private String deliveryCompany;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private UnitType type;

    @Column(columnDefinition = "NUMERIC")
    @NotNull
    private float weight;

    @Column(columnDefinition = "NUMERIC")
    @NotNull
    private float length;

    @Column(columnDefinition = "NUMERIC")
    @NotNull
    private float width;

    @Column(columnDefinition = "NUMERIC")
    @NotNull
    private float height;

    @Column(columnDefinition = "BOOLEAN")
    private boolean sealed;

    @Column(columnDefinition = "BOOLEAN")
    private boolean fragile;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false, referencedColumnName = "id")
    private Order order;
}
