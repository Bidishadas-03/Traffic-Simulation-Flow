package com.traffic.simulation;

import com.traffic.events.SimulationEvent;
import java.util.PriorityQueue;
import java.util.Queue;

public class EventQueue {
    private Queue<SimulationEvent> queue;

    public EventQueue() {
        this.queue = new PriorityQueue<>();
    }

    public void addEvent(SimulationEvent event) {
        queue.add(event);
    }

    public SimulationEvent getNextEvent() {
        return queue.poll();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}