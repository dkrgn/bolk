package bolk_app.reg_login;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotNull
    @Column(columnDefinition = "VARCHAR(255)",
    name = "name")
    private String name;

    @NotNull
    @Column(columnDefinition = "VARCHAR(255)",
    name = "email")
    private String email;

    @NotNull
    @Column(columnDefinition = "VARCHAR(255)",
    name = "password")
    private String password;

    @Column(columnDefinition = "VARCHAR(255)",
    name = "token")
    private String token;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    public User(String name, String email, String password, Role role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }
}
