package vn.cmcglobal.trial.dto;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoriesDTO implements Serializable {
    @Id
    private Long id;
    @NotBlank(message = "Category name is required")
    private String name;
    @Size(max = 1000, message = "Enter description up to 1000 characters")
    private String description;
}
