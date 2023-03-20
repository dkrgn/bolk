package bolk_app.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "units")
public class Unit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;


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

    @Column(columnDefinition = "BOOLEAN")
    private boolean sealed;

    @Column(columnDefinition = "BOOLEAN")
    private boolean fragile;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false, referencedColumnName = "id")
    private Order order;
}
