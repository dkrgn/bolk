package bolk_app.models;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

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

    @Column(columnDefinition = "VARCHAR(10)")
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
}
