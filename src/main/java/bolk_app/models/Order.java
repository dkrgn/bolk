package bolk_app.models;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "orders") //goederenregel
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id; //aantal? (don't know if it is meant as id or number of pallets)

    @Column(columnDefinition = "ENUM('EWPE', 'EURO', 'EWPB', 'EWPHA', 'DOOS', 'PLTVR')")
    @Enumerated(EnumType.ORDINAL)
    private Unit unit;

    @Column(columnDefinition = "VARCHAR(255)")
    @NotNull
    private String description;

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

    @Column(columnDefinition = "NUMERIC")
    @NotNull
    private float lm; //no idea what that is
}

enum Unit {
    EWPE, EURO, EWPB, EWPHA, DOOS, PLTVR
}
