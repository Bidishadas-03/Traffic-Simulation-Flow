package com.traffic.model;

import java.util.Map;

public class Intersection {
    private int id;
    private Map<Direction, RoadSegment> incomingRoads;
    private Map<Direction, RoadSegment> outgoingRoads;
    private TrafficLight trafficLight; // Can be null if it's a simple junction

    public Intersection(int id) {
        this.id = id;
        this.incomingRoads = new java.util.HashMap<>();
        this.outgoingRoads = new java.util.HashMap<>();
    }

    public void addIncomingRoad(Direction direction, RoadSegment road) {
        incomingRoads.put(direction, road);
    }

    public void addOutgoingRoad(Direction direction, RoadSegment road) {
        outgoingRoads.put(direction, road);
    }

    public void setTrafficLight(TrafficLight light) {
        this.trafficLight = light;
    }

    // Getters
}