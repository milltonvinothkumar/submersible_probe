package com.example.probedemo.service;

import com.example.probedemo.model.Direction;
import com.example.probedemo.model.Position;
import org.springframework.stereotype.Service;

@Service
public class ProbeService {

    public Position initProbe(int x, int y, String directionString) {

        Direction direction;
        try {
            direction = Direction.valueOf(directionString.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid direction : " + directionString + ". Use one of : UP, DOWN, RIGHT, LEFT");
        }

        Position position = new Position(x, y, direction);

        return position;
    }
}
