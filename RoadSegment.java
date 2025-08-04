package com.traffic.model;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class RoadSegment {
    private int id;
    private Intersection startIntersection;
    private Intersection endIntersection;
    private double length;
    private int capacity; // Max cars allowed on segment
    private Queue<Car> carsOnSegment; // Consider a more sophisticated structure if order matters

    public RoadSegment(int id, Intersection start, Intersection end, double length, int capacity) {
        this.id = id;
        this.startIntersection = start;
        this.endIntersection = end;
        this.length = length;
        this.capacity = capacity;
        this.carsOnSegment = new ConcurrentLinkedQueue<>(); // Simple for now
    }

    public boolean addCar(Car car) {
        if (carsOnSegment.size() < capacity) {
            return carsOnSegment.add(car);
        }
        return false; // Segment is full
    }

    public boolean removeCar(Car car) {
        return carsOnSegment.remove(car);
    }

    public int getCurrentLoad() {
        return carsOnSegment.size();
    }

    // Getters
}