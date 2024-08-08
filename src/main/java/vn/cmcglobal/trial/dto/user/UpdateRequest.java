package vn.cmcglobal.trial.dto.user;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;
import vn.cmcglobal.trial.validation.image.ValidationImage;
import vn.cmcglobal.trial.validation.password.ValidationPassword;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateRequest {
    String id;

    @NotEmpty(message = "First name not empty.")
    String first_name;

    @NotEmpty(message = "Last name not empty.")
    String last_name;

    @NotEmpty(message = "Email not empty.")
    @Pattern(regexp = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$",
            message = "Email address is not valid.")
    String email;

    @NotNull(message = "Date is required.")
    LocalDate birthday;

    @NotEmpty(message = "Username not empty.")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$",
            message = "Username must contain only letters, numbers, and underscores.")
    String username;

    @ValidationPassword(message = "Password must be at least 8 characters long, contain at least one uppercase letter, one lowercase letter, one number, and one special character.",
            action = "update")
    String password;

    @ValidationImage(action = "edit")
    MultipartFile avatar;

    @NotEmpty(message = "Role not empty.")
    String role;
}
