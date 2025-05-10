package com.autodriving.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DirectionTest {

    @Test
    public void testLeftRotations() {
        assertEquals(Direction.W, Direction.N.left());
        assertEquals(Direction.S, Direction.W.left());
        assertEquals(Direction.E, Direction.S.left());
        assertEquals(Direction.N, Direction.E.left());
    }

    @Test
    public void testRightRotations() {
        assertEquals(Direction.E, Direction.N.right());
        assertEquals(Direction.S, Direction.E.right());
        assertEquals(Direction.W, Direction.S.right());
        assertEquals(Direction.N, Direction.W.right());
    }

    @Test
    public void testFullLeftRotationCycle() {
        Direction d = Direction.N;
        for (int i = 0; i < 4; i++) {
            d = d.left();
        }
        assertEquals(Direction.N, d);
    }

    @Test
    public void testFullRightRotationCycle() {
        Direction d = Direction.N;
        for (int i = 0; i < 4; i++) {
            d = d.right();
        }
        assertEquals(Direction.N, d);
    }

    @Test
    public void testLeftThenRightCancelsOut() {
        for (Direction dir : Direction.values()) {
            assertEquals(dir, dir.left().right(), "Left then right should return original for " + dir);
        }
    }

    @Test
    public void testRightThenLeftCancelsOut() {
        for (Direction dir : Direction.values()) {
            assertEquals(dir, dir.right().left(), "Right then left should return original for " + dir);
        }
    }
}

