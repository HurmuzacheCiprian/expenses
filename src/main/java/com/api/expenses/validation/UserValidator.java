package com.api.expenses.validation;

import java.util.Optional;

public class UserValidator implements Validator {

    @Override
    public void validate(Object object) {
        if (object instanceof Optional) {
            Optional optionalObject = (Optional) object;
            if (!optionalObject.isPresent()) {
                throw new RuntimeException(String.format("Validation for %s failed", object));
            }
        }
    }

}
