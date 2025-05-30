package com.example.probedemo.service;

import com.example.probedemo.exception.InitializeProbeException;
import com.example.probedemo.exception.UnableToMoveException;
import com.example.probedemo.model.Direction;
import com.example.probedemo.model.Position;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ProbeService {

    int seaWidth = 10;
    int seaHeight = 10;

    Set<String> obstacles = new HashSet<>();

    private Set<String> visitedCordinates = new HashSet<>();

    Position position;
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

        position = new Position(x, y, direction, obstacles);
        visitedCordinates.clear();
        visitedCordinates.add(x + "," + y);
        return position;
    }

    public Position executeCommands(List<String> commands) {

        if (position == null)
            throw new InitializeProbeException("Probe position is not initialized yet");
        for (String cmd : commands) {
            switch (cmd) {
                case "TURN_RIGHT" -> position.setDirection(position.getDirection().turnRight());
                case "TURN_LEFT" -> position.setDirection(position.getDirection().turnLeft());
                case "MOVE_FORWARD" -> move(1);
                case "MOVE_BACKWARD" -> move(-1);
            }
        }
        visitedCordinates.add(position.getX() + "," + position.getY());
        return position;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
    private void move(int step) {
        int x = position.getX();
        int y = position.getY();;
        Direction direction = position.getDirection();
        Set<String> obstaclesSet = position.getObstacles();
        switch(direction) {
            case UP -> y += step;
            case DOWN -> y -= step;
            case RIGHT -> x += step;
            case LEFT -> x -= step;
        }

        String positionCordinates = x + "," + y;
        if (x >= 0 && x < seaWidth && y >= 0 && y < seaHeight && !obstaclesSet.contains(positionCordinates)) {

            visitedCordinates.add(positionCordinates);
            position.setX(x);
            position.setY(y);
        } else {
            throw new UnableToMoveException("Blocked by boundary or obstacle at " + positionCordinates);
        }
    }

    public Set<String> getVisitedCordinates() {
        return visitedCordinates;
    }
}
