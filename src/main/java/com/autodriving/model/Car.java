package com.autodriving.model;

import lombok.Getter;
import lombok.ToString;

import java.util.LinkedList;
import java.util.Queue;

@Getter
@ToString(of = {"name", "currentX", "currentY", "dir"})
public class Car {
    private final String name;
    private int currentX;
    private int currentY;
    private Direction dir;
    private final Queue<Character> commands;
    private boolean active = true;

    public Car(String name, int x, int y, Direction dir, String commandStr) {
        this.name = name;
        this.currentX = x;
        this.currentY = y;
        this.dir = dir;
        this.commands = new LinkedList<>();
        for (char c : commandStr.toCharArray()) {
            if (c == 'L' || c == 'R' || c == 'F') {
                commands.add(c);
            } else {
                throw new IllegalArgumentException("Invalid command: " + c);
            }
        }
    }

    public Character nextCommand() {
        return commands.poll();
    }

    public void deactivate() {
        active = false;
    }

    public void applyCommand(char cmd, Field field) {
        if (!active) return;
        switch (cmd) {
            case 'L':
                dir = dir.left();
                break;
            case 'R':
                dir = dir.right();
                break;
            case 'F':
                int newX = currentX;
                int newY = currentY;
                switch (dir) {
                    case N: newY++; break;
                    case S: newY--; break;
                    case E: newX++; break;
                    case W: newX--; break;
                }
                if (field.isInside(newX, newY)) {
                    currentX = newX; currentY = newY;
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown command: " + cmd);
        }
    }
}