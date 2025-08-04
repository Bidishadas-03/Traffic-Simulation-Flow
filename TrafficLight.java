package com.traffic.model;

import com.traffic.simulation.TrafficSimulation;
import com.traffic.events.TrafficLightChangeEvent;
import java.util.Map;

public class TrafficLight {
    private int id;
    private Intersection intersection;
    private Map<Direction, TrafficLightState> states; // Current state for each direction
    private Map<Direction, Double> greenDurations;
    private Map<Direction, Double> yellowDurations;
    private Direction currentGreenDirection;
    private double timeInCurrentPhase;

    public TrafficLight(int id, Intersection intersection, Map<Direction, Double> greenDurations, Map<Direction, Double> yellowDurations) {
        this.id = id;
        this.intersection = intersection;
        this.greenDurations = greenDurations;
        this.yellowDurations = yellowDurations;
        this.states = new java.util.HashMap<>();
        // Initialize all directions to RED
        for (Direction dir : Direction.values()) {
            states.put(dir, TrafficLightState.RED);
        }
        // Pick an initial green direction
        this.currentGreenDirection = Direction.NORTH; // Example
        states.put(currentGreenDirection, TrafficLightState.GREEN);
        this.timeInCurrentPhase = 0;
    }

    public void advancePhase(TrafficSimulation simulation) {
        // Logic to cycle through states: GREEN -> YELLOW -> RED for current direction
        // Then set next direction to GREEN
        // Schedule next TrafficLightChangeEvent
    }

    public TrafficLightState getState(Direction direction) {
        return states.get(direction);
    }

    // Getters and Setters
}