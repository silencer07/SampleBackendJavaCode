package com.englishcentral;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public final class ValidatorUtil {

    public static final Map<String, Object> getOffendingFieldAndValues(Object object, Validator validator){
        Set<ConstraintViolation<Object>> violations = validator.validate(object);

        Map<String, Object> map = new HashMap<>();
        for (ConstraintViolation<Object> violation : violations) {
            String propertyPath = violation.getPropertyPath().toString();
            map.put(propertyPath, violation.getInvalidValue());
        }

        return map;
    }
}
