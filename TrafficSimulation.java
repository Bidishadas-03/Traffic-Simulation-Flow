package com.traffic.simulation;

import com.traffic.events.CarMoveEvent;
import com.traffic.events.SimulationEvent;
import com.traffic.events.TrafficLightChangeEvent;
import com.traffic.model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TrafficSimulation {
    private static double currentSimulationTime;
    private EventQueue eventQueue;
    private StatisticsCollector statsCollector;

    private List<RoadSegment> roadNetwork;
    private List<Intersection> intersections;
    private List<TrafficLight> trafficLights;
    private Map<Integer, Car> cars; // Map to easily access cars by ID

    public TrafficSimulation() {
        this.eventQueue = new EventQueue();
        this.statsCollector = new StatisticsCollector();
        this.roadNetwork = new ArrayList<>();
        this.intersections = new ArrayList<>();
        this.trafficLights = new ArrayList<>();
        this.cars = new ConcurrentHashMap<>();
        currentSimulationTime = 0.0;
    }

    public static double getCurrentSimulationTime() {
        return currentSimulationTime;
    }

    public void setupSimulation(int numCars, double simulationDuration) {
        // 1. Build Road Network (simple example)
        Intersection i1 = new Intersection(1);
        Intersection i2 = new Intersection(2);
        Intersection i3 = new Intersection(3);
        Intersection i4 = new Intersection(4);
        intersections.addAll(Arrays.asList(i1, i2, i3, i4));

        RoadSegment r12 = new RoadSegment(1, i1, i2, 100.0, 10); // I1 to I2
        RoadSegment r21 = new RoadSegment(2, i2, i1, 100.0, 10); // I2 to I1
        RoadSegment r23 = new RoadSegment(3, i2, i3, 80.0, 8);  // I2 to I3
        RoadSegment r32 = new RoadSegment(4, i3, i2, 80.0, 8);  // I3 to I2
        roadNetwork.addAll(Arrays.asList(r12, r21, r23, r32));

        i1.addOutgoingRoad(Direction.EAST, r12);
        i2.addIncomingRoad(Direction.WEST, r12);
        i2.addOutgoingRoad(Direction.EAST, r23);
        i3.addIncomingRoad(Direction.WEST, r23);

        // 2. Setup Traffic Lights (example for i2)
        Map<Direction, Double> greenTimes = Map.of(Direction.WEST, 20.0, Direction.NORTH, 20.0);
        Map<Direction, Double> yellowTimes = Map.of(Direction.WEST, 5.0, Direction.NORTH, 5.0);
        TrafficLight tl2 = new TrafficLight(1, i2, greenTimes, yellowTimes);
        i2.setTrafficLight(tl2);
        trafficLights.add(tl2);

        // 3. Create Cars and initial events
        for (int i = 0; i < numCars; i++) {
            // Randomly pick start and end segments/intersections for cars
            // For simplicity, let's start all cars at r12 and go to i3
            Car car = new Car(i + 1, r12, i3, 10.0); // Speed 10 units/sec
            cars.put(car.getId(), car);
            r12.addCar(car); // Add car to its starting segment
            eventQueue.addEvent(new CarMoveEvent(currentSimulationTime, car, 0.0)); // Initial move event
        }

        // Add initial traffic light change events
        eventQueue.addEvent(new TrafficLightChangeEvent(currentSimulationTime + tl2.getGreenDurations().get(Direction.WEST), tl2));
    }

    public void runSimulation(double maxSimulationTime) {
        System.out.println("Starting Traffic Simulation...");

        while (!eventQueue.isEmpty() && currentSimulationTime < maxSimulationTime) {
            SimulationEvent event = eventQueue.getNextEvent();
            if (event == null) break;

            currentSimulationTime = event.getTime();
            if (currentSimulationTime > maxSimulationTime) {
                break; // Stop if we exceed max simulation time
            }

            System.out.printf("Time: %.2f - Processing Event: %s%n", currentSimulationTime, event.getClass().getSimpleName());
            event.process(this); // Process the event
        }

        System.out.println("Simulation Ended.");
        displayStatistics();
    }

    public EventQueue getEventQueue() {
        return eventQueue;
    }

    public StatisticsCollector getStatsCollector() {
        return statsCollector;
    }

    public RoadSegment getRoadSegmentById(int id) {
        return roadNetwork.stream().filter(r -> r.getId() == id).findFirst().orElse(null);
    }

    public Intersection getIntersectionById(int id) {
        return intersections.stream().filter(i -> i.getId() == id).findFirst().orElse(null);
    }

    public Car getCarById(int id) {
        return cars.get(id);
    }

    private void displayStatistics() {
        System.out.println("\n--- Simulation Statistics ---");
        System.out.printf("Average Travel Time: %.2f units%n", statsCollector.getAverageTravelTime());
        // Add more statistics here
    }
}