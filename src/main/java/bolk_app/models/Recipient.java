package bolk_app.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Set;

/**
 * POJO Recipient object to persist it in database
 */
@Getter
@Setter
@Entity
@Table(name = "recipients") //ontvangers
public class Recipient {

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

    @JsonIgnore
    @OneToMany(mappedBy = "recipient")
    private Set<Order> orderSet;
}
