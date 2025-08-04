package com.traffic.events;

import com.traffic.model.TrafficLight;
import com.traffic.simulation.TrafficSimulation;

public class TrafficLightChangeEvent extends SimulationEvent {
    private TrafficLight light;

    public TrafficLightChangeEvent(double time, TrafficLight light) {
        super(time);
        this.light = light;
    }

    @Override
    public void process(TrafficSimulation simulation) {
        light.advancePhase(simulation);
        // Potentially generate new CarMoveEvents for cars waiting at this intersection
    }
}