package com.autodriving.service;

import com.autodriving.model.Car;
import com.autodriving.model.Field;

import java.util.List;
import java.util.Map;

public interface SimulationService {
    Map<String, String> simulate(Field field, List<Car> cars);
}