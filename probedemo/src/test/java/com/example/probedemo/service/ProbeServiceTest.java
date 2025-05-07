package com.example.probedemo.service;

import com.example.probedemo.model.Direction;
import com.example.probedemo.model.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ProbeServiceTest {

    @Autowired
    private ProbeService probeService;
    private Position position;

    @BeforeEach
    public void setUp() {
    }

    @Test
    public void testInitProbeWithValidDirection() {

        position = probeService.initProbe(1, 2, "UP");
        assertEquals(1, position.getX());
        assertEquals(2, position.getY());
        assertEquals(Direction.UP, position.getDirection());
    }

    @Test
    void testInitProbeInvalidDirection() {
        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                probeService.initProbe(1, 2, "testing"));
        assertTrue(ex.getMessage().contains("Invalid direction"));
    }
}
