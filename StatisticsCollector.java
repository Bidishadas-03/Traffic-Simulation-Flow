package com.traffic.simulation;

import com.traffic.model.Car;
import java.util.ArrayList;
import java.util.List;

public class StatisticsCollector {
    private List<Double> completedTravelTimes;
    // Add more lists/data structures for other metrics

    public StatisticsCollector() {
        this.completedTravelTimes = new ArrayList<>();
    }

    public void recordCarArrival(Car car) {
        completedTravelTimes.add(car.getTravelTime());
    }

    public double getAverageTravelTime() {
        if (completedTravelTimes.isEmpty()) {
            return 0.0;
        }
        return completedTravelTimes.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
    }

    // Methods to get other statistics (e.g., congestion levels)
}