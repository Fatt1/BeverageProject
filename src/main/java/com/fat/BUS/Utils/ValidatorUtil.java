package com.fat.BUS.Utils;

import com.fat.Contract.Exceptions.ValidationException;
import jakarta.validation.*;

import java.util.Set;

public class ValidatorUtil {
    private static final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private static final Validator validator = factory.getValidator();

    public static <T> void validate(T object){
        Set<ConstraintViolation<T>> validations = validator.validate(object);

        if(!validations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            validations.stream().forEach(x -> {;
                sb.append(x.getPropertyPath()).append(": ").append(x.getMessage()).append("\n");
            });
            throw new ValidationException(sb.toString());
        }
    }
}
