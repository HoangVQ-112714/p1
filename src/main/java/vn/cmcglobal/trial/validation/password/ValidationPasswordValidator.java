package vn.cmcglobal.trial.validation.password;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidationPasswordValidator implements ConstraintValidator<ValidationPassword, String> {
    private String message;
    private String action;

    @Override
    public void initialize(ValidationPassword validationPassword) {
        this.message = validationPassword.message();
        this.action = validationPassword.action();
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
        if (action.equals("create")) {
            if (password.isEmpty()) {
                return false;
            }
        }

        if (!password.isEmpty()) {
            String pattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$.%*?&])[A-Za-z\\d@$.%*?&]{8,}$";
            return password.matches(pattern);
        }

        return true;
    }
}
