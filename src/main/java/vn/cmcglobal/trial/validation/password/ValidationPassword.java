package vn.cmcglobal.trial.validation.password;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = ValidationPasswordValidator.class)
public @interface ValidationPassword {
    String message();
    String action();

    Class<? extends Payload>[] payload() default {};
    Class<?>[] groups() default {};
}
