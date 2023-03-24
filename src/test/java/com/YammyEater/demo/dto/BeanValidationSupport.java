package com.YammyEater.demo.dto;

import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public interface BeanValidationSupport {

    default Validator getValidator() {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        return validatorFactory.getValidator();
    }

    default <T extends ConstraintViolation, E> boolean checkConstraintViolation(Set<T> violations, Class<E> targ) {
        return violations.stream()
                .anyMatch(violation -> targ.isInstance(violation.getConstraintDescriptor().getAnnotation()));
    }
}
