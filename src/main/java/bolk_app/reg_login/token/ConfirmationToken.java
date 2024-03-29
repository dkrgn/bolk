package bolk_app.reg_login.token;

import bolk_app.reg_login.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * POJO ConfirmationToken object to persist it in database
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "confirmation_tokens")
public class ConfirmationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String token;
    @Column(nullable = false,
    name = "created_at")
    private LocalDateTime createdAt;
    @Column(nullable = false,
    name = "expires_at")
    private LocalDateTime expiresAt;

    @ManyToOne
    @JoinColumn(nullable = false,
    name = "user_id")
    private User user;

    public ConfirmationToken(String token, LocalDateTime createdAt, LocalDateTime expiresAt, User user) {
        this.token = token;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.user = user;
    }
}
