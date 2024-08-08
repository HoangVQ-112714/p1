package vn.cmcglobal.trial.validation.image;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;

public class ValidationImageValidator implements ConstraintValidator<ValidationImage, MultipartFile> {
    private String action;
    private String message;

    @Override
    public void initialize(ValidationImage constraintAnnotation) {
        action = constraintAnnotation.action();
        message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext constraintValidatorContext) {
        boolean check = true;

        if (action.equals("create")) {
            if (file.isEmpty()) {
                check = false;
            } else {
                check = checkContentType(file.getContentType());
                if (!check) {
                    message = "Only \"image/jpeg\", \"image/png\", \"image/gif\" file formats are allowed.";
                }
            }
        } else {
            if (!file.isEmpty()) {
                check = checkContentType(file.getContentType());
                if (!check) {
                    message = "Only \"image/jpeg\", \"image/png\", \"image/gif\" file formats are allowed.";
                }
            }
        }

        if (!check) {
            constraintValidatorContext.buildConstraintViolationWithTemplate(message)
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
        }

        return check;
    }

    /**
     *
     *
     * @param contentType content tyoe
     * @return Boolean
     */
    private Boolean checkContentType(String contentType) {
        String[] list = {"image/jpeg", "image/png", "image/gif"};

        return Arrays.asList(list).contains(contentType);
    }


}
