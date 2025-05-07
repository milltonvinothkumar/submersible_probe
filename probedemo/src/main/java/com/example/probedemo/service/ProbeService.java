package com.example.probedemo.service;

import com.example.probedemo.model.Direction;
import com.example.probedemo.model.Position;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class ProbeService {

    int seaWidth = 10;
    int seaHeight = 10;

    Set<String> obstacles = new HashSet<>();
    public Position initProbe(int x, int y, String directionString, Set<String> obstacles) {

        Direction direction;
        if (x < 0 || x > seaWidth || y < 0 || y > seaHeight) {
            throw new IllegalArgumentException("Initial positions are out of boundary : (" + x + "," + y + ")");
        }

        if (obstacles.contains(x + "," + y)) {
            throw new IllegalArgumentException("Initial positions are blocked by obstacles : (" + x + "," + y + ")");
        }

        try {
            direction = Direction.valueOf(directionString.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid direction : " + directionString + ". Use one of : UP, DOWN, RIGHT, LEFT");
        }

        Position position = new Position(x, y, direction, obstacles);

        return position;
    }
}
