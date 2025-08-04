package com.traffic.model;

import com.traffic.simulation.TrafficSimulation;

public class Car {
    private int id;
    private RoadSegment currentSegment;
    private Intersection destinationIntersection;
    private double currentPositionOnSegment; // 0.0 to length of segment
    private double speed; // units per simulation tick/time
    private double startTime;
    private double arrivalTime;
    private boolean finished;

    public Car(int id, RoadSegment startSegment, Intersection destination, double speed) {
        this.id = id;
        this.currentSegment = startSegment;
        this.destinationIntersection = destination;
        this.currentPositionOnSegment = 0.0;
        this.speed = speed;
        this.finished = false;
        this.startTime = TrafficSimulation.getCurrentSimulationTime(); // Capture start time
    }

    // Getters and Setters

    public void move(double timeDelta) {
        // Calculate new position based on speed and timeDelta
        // Handle moving to the next segment or arriving at an intersection
    }

    public void setArrivalTime(double arrivalTime) {
        this.arrivalTime = arrivalTime;
        this.finished = true;
    }

    public double getTravelTime() {
        return arrivalTime - startTime;
    }
}