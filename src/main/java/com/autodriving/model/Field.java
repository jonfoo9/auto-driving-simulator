package com.autodriving.model;


public record Field(int width, int height) {
    public Field {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Field dimensions must be positive");
        }
    }

    public boolean isInside(int row, int col) {
        return col >= 0 && col < width && row >= 0 && row < height;
    }
}