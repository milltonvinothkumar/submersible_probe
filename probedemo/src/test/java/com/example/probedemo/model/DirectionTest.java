package com.example.probedemo.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DirectionTest {
    @Test
    void testTurnRightFromUp() {
        assertEquals(Direction.RIGHT, Direction.UP.turnRight());
    }
}
