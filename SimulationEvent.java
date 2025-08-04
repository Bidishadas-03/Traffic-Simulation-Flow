package com.traffic.events;

public abstract class SimulationEvent implements Comparable<SimulationEvent> {
    private double time; // Time at which this event occurs

    public SimulationEvent(double time) {
        this.time = time;
    }

    public double getTime() {
        return time;
    }

    // Abstract method to be implemented by concrete event types
    public abstract void process(com.traffic.simulation.TrafficSimulation simulation);

    @Override
    public int compareTo(SimulationEvent other) {
        return Double.compare(this.time, other.time);
    }
}