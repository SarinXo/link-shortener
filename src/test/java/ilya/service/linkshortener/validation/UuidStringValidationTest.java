package ilya.service.linkshortener.validation;


import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UuidStringValidationTest {
    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void whenValidUuid_thenEmptyViolations() {
        //given
        UuidStringValidationDto dto = new UuidStringValidationDto("123e4567-e89b-12d3-a456-426614174000");

        //when
        Set<ConstraintViolation<UuidStringValidationDto>> violations = validator.validate(dto);

        //then
        assertTrue(violations.isEmpty(), "Invalid UUID");
    }

    @Test
    public void whenInvalidGuid_thenCreateViolation() {
        //given
        UuidStringValidationDto dto = new UuidStringValidationDto("invalid-uuid");

        //when
        Set<ConstraintViolation<UuidStringValidationDto>> violations = validator.validate(dto);

        //then
        assertFalse(violations.isEmpty(), "Invalid UUID");
        assertEquals(1, violations.size());
        assertEquals("uuid", violations.iterator().next().getPropertyPath().toString());
    }

    @Test
    public void whenNullGuid_thenCreateViolation() {
        //given
        UuidStringValidationDto dto = new UuidStringValidationDto(null);

        //when
        Set<ConstraintViolation<UuidStringValidationDto>> violations = validator.validate(dto);

        //then
        assertTrue(violations.isEmpty(), "Invalid UUID");
    }
}
