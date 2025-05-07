package com.example.probedemo.model;

import java.util.Set;

public class Position {

    private int x;
    private int y;
    private Direction direction;

    private Set<String> obstacles;

    public Position(int x, int y, Direction direction, Set<String> obstacles) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.obstacles = obstacles;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
