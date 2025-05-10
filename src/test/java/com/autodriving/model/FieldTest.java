package com.autodriving.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FieldTest {

    @Test
    void testConstructorValid() {
        Field f = new Field(5, 10);
        assertEquals(5, f.width(),  "Width should be set to the constructor value");
        assertEquals(10, f.height(), "Height should be set to the constructor value");
    }

    @ParameterizedTest(name = "width={0}, height={1}")
    @CsvSource({
            "0, 5",
            "5, 0",
            "-1, 5",
            "5, -1",
            "0, 0",
            "-1, -1"
    })
    void testConstructorInvalid(int width, int height) {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> new Field(width, height),
                "Expected IllegalArgumentException for width=" + width + ", height=" + height
        );
        assertEquals("Field dimensions must be positive", ex.getMessage());
    }
}
