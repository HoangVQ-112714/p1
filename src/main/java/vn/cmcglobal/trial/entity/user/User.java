package vn.cmcglobal.trial.entity.user;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;
@Data
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    String first_name;
    String last_name;
    String avatar;
    LocalDate birthday;
    String username;
    String email;
    String password;

    @Enumerated(EnumType.STRING)
    EnumRoles role;
}
