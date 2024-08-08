package vn.cmcglobal.trial.validation.image;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = ValidationImageValidator.class)
public @interface ValidationImage {
    String message() default "Image is required.";

    String action();

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
