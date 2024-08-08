package vn.cmcglobal.trial.payload.request.post;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
import lombok.*;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;
import vn.cmcglobal.trial.validation.image.ValidationImage;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostRequest {
    Long id;
    @Size(max = 255, message = "Title should must be less than 255 characters")
    @NotBlank(message = "Title is required field!")
    String title;
    @NotBlank(message = "Title is required field!")
    String content;
    @ValidationImage(action = "edit")
    MultipartFile thumbnail;
    Timestamp createdAt;
    Timestamp updatedAt;
}
