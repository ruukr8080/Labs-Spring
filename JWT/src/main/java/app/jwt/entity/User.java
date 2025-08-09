package app.jwt.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;

    @Enumerated(EnumType.STRING)
    UserRole role;

}