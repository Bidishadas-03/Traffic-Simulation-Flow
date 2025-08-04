// Inside CarMoveEvent.java, process method:
@Override
public void process(TrafficSimulation simulation) {
    Car car = this.car;

    // A car has reached the end of its current segment or needs to evaluate next move
    RoadSegment currentSegment = car.getCurrentSegment();
    Intersection nextIntersection = currentSegment.getEndIntersection();

    if (car.getDestinationIntersection().equals(nextIntersection)) {
        // Car reached its final destination
        car.setArrivalTime(simulation.getCurrentSimulationTime());
        simulation.getStatsCollector().recordCarArrival(car);
        currentSegment.removeCar(car); // Remove car from the road network
        System.out.printf("Car %d arrived at destination %s at time %.2f%n", car.getId(), nextIntersection.getId(), simulation.getCurrentSimulationTime());
        return; // Car has finished
    }

    // Car needs to proceed through an intersection or to the next segment
    // For simplicity, let's assume direct movement for now
    // In a real scenario, you'd determine the next road segment based on car's route
    RoadSegment nextSegment = simulation.getRoadSegmentById(currentSegment.getId() + 1); // VERY simplified routing

    if (nextSegment == null) {
        System.err.println("Error: No next segment found for car " + car.getId());
        return; // Should not happen in a well-defined network
    }

    // Check Traffic Light status at the intersection
    TrafficLight light = nextIntersection.getTrafficLight();
    boolean canProceed = true; // Assume can proceed if no light or light is green
    if (light != null) {
        // Determine the direction the car is coming from (e.g., if currentSegment is r12, car is coming from WEST to i2)
        Direction carIncomingDirection = null; // Logic to determine this based on currentSegment and Intersection
        if (nextIntersection.getIncomingRoads().get(Direction.WEST) == currentSegment) { // Example
            carIncomingDirection = Direction.WEST;
        }
        // ... more sophisticated mapping needed

        if (carIncomingDirection != null && light.getState(carIncomingDirection) != TrafficLightState.GREEN) {
            canProceed = false;
        }
    }

    if (canProceed && nextSegment.addCar(car)) {
        // Car can move to the next segment
        currentSegment.removeCar(car); // Remove from old segment
        car.setCurrentSegment(nextSegment);
        car.setCurrentPositionOnSegment(0.0); // Reset position on new segment

        // Schedule next CarMoveEvent (when it reaches the end of the new segment)
        double timeToReachEndOfNextSegment = nextSegment.getLength() / car.getSpeed();
        simulation.getEventQueue().addEvent(new CarMoveEvent(simulation.getCurrentSimulationTime() + timeToReachEndOfNextSegment, car, nextSegment.getLength()));
        System.out.printf("Car %d moved to segment %d at time %.2f%n", car.getId(), nextSegment.getId(), simulation.getCurrentSimulationTime());

    } else {
        // Car cannot proceed (due to red light or congestion in next segment)
        // Re-schedule this car's event for a short time in the future
        // A more advanced approach might involve active waiting lists for cars at intersections
        double retryTime = simulation.getCurrentSimulationTime() + 1.0; // Retry after 1 time unit
        simulation.getEventQueue().addEvent(new CarMoveEvent(retryTime, car, car.getCurrentPositionOnSegment())); // Stay in place, try again
        System.out.printf("Car %d waiting at segment %d (blocked) at time %.2f%n", car.getId(), currentSegment.getId(), simulation.getCurrentSimulationTime());
    }
}