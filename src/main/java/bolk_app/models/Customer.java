package bolk_app.models;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Set;

@Data
@Entity
@Table(name = "customers") //klanten
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(columnDefinition = "VARCHAR(255)")
    @NotNull
    private String name;

    @Column(columnDefinition = "VARCHAR(255)")
    @NotNull
    private String street;

    @Column(columnDefinition = "VARCHAR(10)",
            name = "house_nr")
    private String houseNr;

    @Column(columnDefinition = "VARCHAR(10)")
    private String addition;

    @Column(columnDefinition = "VARCHAR(10)",
            name = "country_code")
    @NotNull
    private String countryCode;

    @Column(columnDefinition = "VARCHAR(10)",
            name = "zip_code")
    @NotNull
    private String zipCode;

    @Column(columnDefinition = "VARCHAR(255)")
    @NotNull
    private String city;

    @Column(columnDefinition = "DATE",
            name = "date")
    private LocalDate date;

    @OneToMany(mappedBy = "customer")
    private Set<Order> orderSet;

}
