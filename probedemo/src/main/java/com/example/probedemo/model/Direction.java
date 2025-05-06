package com.example.probedemo.model;

public enum Direction {

    UP, RIGHT, DOWN, LEFT;

    public Direction turnLeft() {
        return null;
    }

    public Direction turnRight() {
        return switch(this) {
            case UP -> RIGHT;
            case RIGHT -> DOWN;
            case DOWN -> LEFT;
            case LEFT -> UP;
        };
    }
}
