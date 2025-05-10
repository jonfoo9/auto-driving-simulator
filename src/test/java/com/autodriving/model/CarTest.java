package com.autodriving.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CarTest {
    private Field field;

    @BeforeEach
    public void setup() {
        field = new Field(5, 5);
    }

    @Test
    public void testConstructorAndGetters() {
        Car car = new Car("A", 2, 3, Direction.N, "LRF");
        assertEquals("A", car.getName());
        assertEquals(2, car.getCurrentX());
        assertEquals(3, car.getCurrentY());
        assertEquals(Direction.N, car.getDir());
    }

    @Test
    public void testNextCommandDequeuesCorrectly() {
        Car car = new Car("B", 0, 0, Direction.E, "FLR");
        assertEquals(Character.valueOf('F'), car.nextCommand());
        assertEquals(Character.valueOf('L'), car.nextCommand());
        assertEquals(Character.valueOf('R'), car.nextCommand());
        assertNull(car.nextCommand());
    }

    @Test
    public void testApplyTurnCommands() {
        Car car = new Car("C", 0, 0, Direction.N, "L");
        car.applyCommand('L', field);
        assertEquals(Direction.W, car.getDir());

        car = new Car("C2", 0, 0, Direction.S, "R");
        car.applyCommand('R', field);
        assertEquals(Direction.W, car.getDir());
    }

    @Test
    public void testApplyForwardWithinBounds() {
        Car car = new Car("D", 1, 1, Direction.N, "F");
        car.applyCommand('F', field);
        assertEquals(1, car.getCurrentX());
        assertEquals(2, car.getCurrentY());

        car = new Car("E", 1, 1, Direction.E, "F");
        car.applyCommand('F', field);
        assertEquals(2, car.getCurrentX());
        assertEquals(1, car.getCurrentY());
    }

    @Test
    public void testApplyForwardOutOfBoundsIgnored() {
        Car car = new Car("F", 0, 0, Direction.S, "F");
        car.applyCommand('F', field);
        assertEquals(0, car.getCurrentX());
        assertEquals(0, car.getCurrentY());

        car = new Car("G", 4, 4, Direction.N, "F");
        car.applyCommand('F', field);
        assertEquals(4, car.getCurrentX());
        assertEquals(4, car.getCurrentY());
    }

    @Test
    public void testDeactivateStopsFurtherCommands() {
        Car car = new Car("H", 2, 2, Direction.N, "FFFF");
        car.deactivate();
        car.applyCommand('F', field);
        assertEquals(2, car.getCurrentX());
        assertEquals(2, car.getCurrentY());
    }

    @Test
    public void testInvalidCommandThrows() {
        assertThrows(IllegalArgumentException.class, () -> new Car("X", 0, 0, Direction.N, "AB"));
        Car car = new Car("Y", 0, 0, Direction.N, "LRF");
        assertThrows(IllegalArgumentException.class, () -> car.applyCommand('Z', field));
    }
}