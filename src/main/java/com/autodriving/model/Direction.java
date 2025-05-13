package com.autodriving.model;

public enum Direction {
  N,
  E,
  S,
  W;

  public Direction left() {
    return switch (this) {
      case N -> W;
      case W -> S;
      case S -> E;
      case E -> N;
    };
  }

  public Direction right() {
    return switch (this) {
      case N -> E;
      case E -> S;
      case S -> W;
      case W -> N;
    };
  }
}
