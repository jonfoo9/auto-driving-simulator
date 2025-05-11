# Auto Driving Car Simulation

This project is a simulation program for autonomous driving cars on a rectangular field. It uses Java, Gradle, and Lombok to reduce boilerplate. 

Unit tests are written with JUnit 5.

## Requirements
- Java 21

## Modules and Classes
- **com.example.autodriving.Direction**: Enum for directions with rotation logic. Used to keep track of absolute direction of each car on field. 
- **com.example.autodriving.Field**: Simulation field, has logic to ensure cars are within boundary. 
- **com.example.autodriving.Car**: Car with name, position, direction, and commands.
- **com.example.autodriving.SimulationService**: Interface defining the simulation interface. 
- **com.example.autodriving.SimulationServiceImpl**: Implements step by step simulation and checking collisions
- **com.example.autodriving.Main**: CLI entry point.

## How to Run
```bash
./gradlew build
./gradlew run
```

Also, to execute tests:
```bash
./gradlew test
```

## Assumptions
- Cars ignoring out-of-bounds F commands remain in place.
- Collisions stop only the collided cars, others continue.
- CLI is simple sequential prompt-based, no parallel input.

## Areas for Improvement
- Add persistence (e.g., save/restore field).
- Enhance CLI with input validation and error handling.

