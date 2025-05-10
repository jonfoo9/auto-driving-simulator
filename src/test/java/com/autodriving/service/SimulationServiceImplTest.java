package com.autodriving.service;

import com.autodriving.model.Car;
import com.autodriving.model.Direction;
import com.autodriving.model.Field;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SimulationServiceImplTest {

    private SimulationServiceImpl service;
    private Field field;

    @BeforeEach
    void setUp() {
        service = new SimulationServiceImpl();
        field = new Field(10, 10);
    }

    @Test
    void simulate_NoCars_ReturnsEmptyMap() {
        Map<String, String> result = service.simulate(field, Collections.emptyList());
        assertTrue(result.isEmpty(), "With no cars, result should be empty");
    }

    @Test
    void simulate_SingleCar_NoCollision() {
        Car car = new Car("A", 1, 2, Direction.N, "FFRFF");
        Map<String, String> result = service.simulate(field, List.of(car));

        assertEquals(1, result.size(), "Should have one entry");
        assertEquals("(3,4) E", result.get("A"), "Final position and heading mismatch");
    }

    @Test
    void simulate_MultipleCars_NoCollision_PreservesInsertionOrder() {
        Car a = new Car("A", 0, 0, Direction.N, "FF");
        Car b = new Car("B", 1, 1, Direction.E, "F");

        Map<String, String> result = service.simulate(field, List.of(a, b));

        assertEquals(2, result.size());
        assertEquals("(0,2) N", result.get("A"));
        assertEquals("(2,1) E", result.get("B"));

        List<String> keys = new ArrayList<>(result.keySet());
        assertEquals(List.of("A", "B"), keys, "Result keys should be in the same order as input cars");
    }

    @Test
    void simulate_Collision_BothCarsReportCollisionAtSameStep() {
        Car a = new Car("A", 0, 0, Direction.N, "FF");
        Car b = new Car("B", 0, 2, Direction.S, "FF");

        Map<String, String> result = service.simulate(field, List.of(a, b));

        assertEquals(2, result.size());
        String aRes = result.get("A");
        String bRes = result.get("B");

        assertTrue(aRes.contains("collides with B at (0,1) at step 1"),
                "A should report collision with B at (0,1) step 1");
        assertTrue(bRes.contains("collides with A at (0,1) at step 1"),
                "B should report collision with A at (0,1) step 1");
    }

    @Test
    void simulate_IrregularCommandLengths_CarStopsWhenCommandsExhausted() {
        Car a = new Car("A", 1, 1, Direction.E, "F");
        Car b = new Car("B", 2, 2, Direction.W, "FFF");

        Map<String, String> result = service.simulate(field, List.of(a, b));

        assertEquals("(2,1) E", result.get("A"));
        assertEquals("(0,2) W", result.get("B"));
    }

    @Test
    void simulate_simulationContinuesAfterCarsCollide() {
        Car a = new Car("A", 0, 0, Direction.E, "F");
        Car b = new Car("B", 1, 0, Direction.W, "");
        Car c = new Car("C", 5,5,Direction.W, "FFFF");

        Map<String, String> result = service.simulate(field, List.of(a, b, c));


        assertEquals("collides with B at (1,0) at step 1",result.get("A"));
        assertEquals("collides with A at (1,0) at step 1",result.get("B"));
        assertEquals("(1,5) W",result.get("C"));




    }
}