package com.example.probedemo.service;

import com.example.probedemo.exception.InitializeProbeException;
import com.example.probedemo.exception.UnableToMoveException;
import com.example.probedemo.model.Direction;
import com.example.probedemo.model.Position;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ProbeServiceTest {

    @Autowired
    private ProbeService probeService;
    private Position position;

    @Test
    public void testInitProbeWithValidDirection() {

        position = probeService.initProbe(1, 2, "UP", new HashSet<>());
        assertEquals(1, position.getX());
        assertEquals(2, position.getY());
        assertEquals(Direction.UP, position.getDirection());
    }

    @Test
    void testInitProbeInvalidBoundary() {
        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                probeService.initProbe(-1, 20, "UP", new HashSet<>()));
        assertTrue(ex.getMessage().contains("Initial positions are out of boundary :"));
    }

    @Test
    void testInitProbeWithObstacles() {
        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                probeService.initProbe(1, 2, "UP", Set.of("1,2")));
        assertTrue(ex.getMessage().contains("Initial positions are blocked by obstacles :"));
    }
    @Test
    void testInitProbeInvalidDirection() {
        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                probeService.initProbe(1, 2, "testing", new HashSet<>()));
        assertTrue(ex.getMessage().contains("Invalid direction"));
    }

    @Test
    void testTurnRight() {
        position = probeService.initProbe(1, 2, "UP", new HashSet<>());
        probeService.executeCommands(List.of("TURN_RIGHT"));
        assertEquals(Direction.RIGHT, probeService.getPosition().getDirection());
    }

    @Test
    void testExecuteCommandsWithoutInitailizingProbe() {
        probeService.setPosition(null);
        Exception ex = assertThrows(InitializeProbeException.class, () ->
                probeService.executeCommands(List.of("TURN_RIGHT")));
        assertTrue(ex.getMessage().contains("Probe position is not initialized yet"));
    }

    @Test
    void testTurnLeft() {
        position = probeService.initProbe(1, 2, "UP", new HashSet<>());
        probeService.executeCommands(List.of("TURN_LEFT"));
        assertEquals(Direction.LEFT, probeService.getPosition().getDirection());
    }

    @Test
    void testMoveForward() {
        position = probeService.initProbe(1, 2, "UP", new HashSet<>());
        probeService.executeCommands(List.of("MOVE_FORWARD"));
        assertEquals(1, position.getX());
        assertEquals(3, position.getY());
    }

    @Test
    void testMoveBackward() {
        position = probeService.initProbe(1, 2, "UP", new HashSet<>());
        probeService.executeCommands(List.of("MOVE_BACKWARD"));
        assertEquals(1, position.getX());
        assertEquals(1, position.getY());
    }

    @Test
    void testMoveBackwardFromDown() {
        position = probeService.initProbe(1, 2, "DOWN", new HashSet<>());
        probeService.executeCommands(List.of("MOVE_BACKWARD"));
        assertEquals(1, position.getX());
        assertEquals(3, position.getY());
    }

    @Test
    void testMoveBackwardFromLeft() {
        position = probeService.initProbe(1, 2, "LEFT", new HashSet<>());
        probeService.executeCommands(List.of("MOVE_BACKWARD"));
        assertEquals(2, position.getX());
        assertEquals(2, position.getY());
    }

    @Test
    void testMoveBackwardFromRight() {
        position = probeService.initProbe(1, 2, "RIGHT", new HashSet<>());
        probeService.executeCommands(List.of("MOVE_BACKWARD"));
        assertEquals(0, position.getX());
        assertEquals(2, position.getY());
    }

    @Test
    void testMoveToInvalidBoundary() {
        position = probeService.initProbe(0, 2, "RIGHT", new HashSet<>());
        Exception ex = assertThrows(UnableToMoveException.class, () ->
                probeService.executeCommands(List.of("MOVE_BACKWARD")));
        assertTrue(ex.getMessage().contains("Blocked by boundary or obstacle at "));
    }

    @Test
    void testMoveToOutOfBoundaryWidth() {
        position = probeService.initProbe(probeService.seaWidth, 2, "RIGHT", new HashSet<>());
        Exception ex = assertThrows(UnableToMoveException.class, () ->
                probeService.executeCommands(List.of("MOVE_FORWARD")));
        assertTrue(ex.getMessage().contains("Blocked by boundary or obstacle at "));
    }

    @Test
    void testMoveToOutOfBoundaryHeight() {
        position = probeService.initProbe(1, probeService.seaHeight, "UP", new HashSet<>());
        Exception ex = assertThrows(UnableToMoveException.class, () ->
                probeService.executeCommands(List.of("MOVE_FORWARD")));
        assertTrue(ex.getMessage().contains("Blocked by boundary or obstacle at "));
    }

    @Test
    void testMoveToInvalidHeight() {
        position = probeService.initProbe(1, 0, "UP", new HashSet<>());
        Exception ex = assertThrows(UnableToMoveException.class, () ->
                probeService.executeCommands(List.of("MOVE_BACKWARD")));
        assertTrue(ex.getMessage().contains("Blocked by boundary or obstacle at "));
    }
}
