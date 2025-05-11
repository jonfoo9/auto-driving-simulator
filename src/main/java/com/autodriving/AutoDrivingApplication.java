package com.autodriving;

import com.autodriving.model.Car;
import com.autodriving.model.Field;
import com.autodriving.model.Direction;
import com.autodriving.service.SimulationService;
import com.autodriving.service.SimulationServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class AutoDrivingApplication {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Welcome to Auto Driving Car Simulation!");
            System.out.print("Please enter the width and height of the simulation field in x y format: ");
            String line = scanner.nextLine().trim();
            while (line.isEmpty()) {
                line = scanner.nextLine().trim();
            }
            String[] dims = line.split("\s+");
            if (dims.length != 2) {
                System.out.println("Invalid input. Please enter two numbers separated by space.");
                continue;
            }
            int w, h;
            try {
                w = Integer.parseInt(dims[0]);
                h = Integer.parseInt(dims[1]);
            } catch (NumberFormatException e) {
                System.out.println("Invalid numbers. Please try again.");
                continue;
            }
            Field field = new Field(w, h);
            System.out.printf("You have created a field of %d x %d.%n%n", w, h);

            List<Car> cars = new ArrayList<>();
            while (true) {
                System.out.println("Please choose from the following options:");
                System.out.println("[1] Add a car to field");
                System.out.println("[2] Run simulation");
                System.out.print("> ");
                String choiceLine = scanner.nextLine().trim();
                if (choiceLine.isEmpty()) continue;
                int choice;
                try {
                    choice = Integer.parseInt(choiceLine);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid choice. Try again.");
                    continue;
                }
                if (choice == 1) {
                    System.out.print("Please enter the name of the car: ");
                    String name = scanner.nextLine().trim();
                    if (name.isEmpty()) {
                        System.out.println("Name cannot be empty.");
                        continue;
                    }
                    System.out.print("Please enter initial position of car " + name + " in x y Direction format: ");
                    line = scanner.nextLine().trim();
                    String[] parts = line.split("\s+");
                    if (parts.length != 3) {
                        System.out.println("Invalid input. Expected format: x y D.");
                        continue;
                    }
                    int x, y;
                    try {
                        x = Integer.parseInt(parts[0]);
                        y = Integer.parseInt(parts[1]);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid coordinates. Try again.");
                        continue;
                    }
                    Direction dir;
                    try {
                        dir = Direction.valueOf(parts[2]);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Invalid direction. Use N, S, E, or W.");
                        continue;
                    }
                    System.out.print("Please enter the commands for car " + name + ": ");
                    String cmds = scanner.nextLine().trim();
                    cars.add(new Car(name, x, y, dir, cmds));
                    System.out.println("Your current list of cars are:");
                    for (Car c : cars) {
                        System.out.printf("- %s, (%d,%d) %s, %s%n",
                                c.getName(), c.getCurrentX(), c.getCurrentY(), c.getDir(), cmds);
                    }
                    System.out.println();
                } else if (choice == 2) {
                    SimulationService sim = new SimulationServiceImpl();
                    System.out.println("After simulation, the result is:");
                    Map<String, String> res = sim.simulate(field, cars);
                    for (Map.Entry<String, String> e : res.entrySet()) {
                        System.out.printf("- %s, %s%n", e.getKey(), e.getValue());
                    }
                    System.out.println();
                    System.out.println("Please choose from the following options:");
                    System.out.println("[1] Start over");
                    System.out.println("[2] Exit");
                    System.out.print("> ");
                    line = scanner.nextLine().trim();
                    if ("1".equals(line)) {
                        break; // restart outer loop
                    } else {
                        System.out.println("Thank you for running the simulation. Goodbye!");
                        System.exit(0);
                    }
                } else {
                    System.out.println("Invalid choice. Try again.");
                }
            }
        }
    }
}
