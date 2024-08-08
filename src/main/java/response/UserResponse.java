package response;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import lombok.experimental.FieldDefaults;
import vn.cmcglobal.trial.entity.user.EnumRoles;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    String id;
    String first_name;
    String last_name;
    String avatar;
    LocalDate birthday;
    String username;
    String email;
    String password;
    EnumRoles role;
}
