package com.autodriving.service;

import com.autodriving.model.Car;
import com.autodriving.model.Field;
import com.google.inject.Inject;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SimulationServiceImpl implements SimulationService {
    @Inject
    public SimulationServiceImpl() {}

    @Override
    public Map<String, String> simulate(Field field, List<Car> cars) {
        Map<String, String> results = new LinkedHashMap<>();
        int step = 0;
        while (true) {
            step++;
            boolean anyActionThisStep = false;

            for (Car car : cars) {
                if (!car.isActive()) continue;

                Character cmd = car.nextCommand();
                if (cmd == null) continue;

                anyActionThisStep = true;
                car.applyCommand(cmd, field);

                for (Car other : cars) {
                    if (other == car || !other.isActive()) continue;

                    if (car.getCurrentX() == other.getCurrentX()
                            && car.getCurrentY() == other.getCurrentY()) {

                        car.deactivate();
                        other.deactivate();

                        results.putIfAbsent(car.getName(),
                                String.format("collides with %s at (%d,%d) at step %d",
                                        other.getName(), car.getCurrentX(), car.getCurrentY(), step));

                        results.putIfAbsent(other.getName(),
                                String.format("collides with %s at (%d,%d) at step %d",
                                        car.getName(), other.getCurrentX(), other.getCurrentY(), step));
                    }
                }
            }

            if (!anyActionThisStep) break;
        }

        for (Car car : cars) {
            if (!results.containsKey(car.getName())) {
                results.put(car.getName(),
                        String.format("(%d,%d) %s",
                                car.getCurrentX(), car.getCurrentY(), car.getDir()));
            }
        }

        return results;
    }

}