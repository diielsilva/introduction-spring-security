package com.dev.storesystem.common.mappers.validators;

import com.dev.storesystem.domain.factories.UserFactory;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(value = SpringExtension.class)
class BeanValidationTest {
    private Validator validator;

    @BeforeEach
    void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void assertNotHaveViolations_WhenCorrectUserDtoIsGiven() {
        var saveUser = UserFactory.generateValidSaveUserDto();
        var violations = validator.validate(saveUser);
        assertNotNull(violations);
        assertTrue(violations.isEmpty());
    }

    @Test
    void assertHaveViolations_WhenIncorrectUserDtoIsGiven() {
        var saveUser = UserFactory.generateEmptySaveUserDto();
        var violations = validator.validate(saveUser);
        assertNotNull(violations);
        assertFalse(violations.isEmpty());
        assertEquals(4, violations.size());
    }
}
