package com.example.number;

import java.util.HashSet;

/**
 * At any given time it can tell 3 things:
 * 1) the min number it has encountered so far
 * 2) the max number it has encountered so far
 * 3) the average of all numbers it has encountered so far
 * <p>
 * assume average of distinct numbers
 */
public class DistinctNumberOffered extends NumberOffered {
    private HashSet<Double> unical = new HashSet<>();

    /**
     * offer a number, non-distinct number will be ignored
     *
     * @param x number
     */
    @Override
    public void offer(double x) {
        synchronized (this) {
            if(unical.add(x))
                super.offer(x);
        }
    }

    /**
     * offer an array of numbers
     *
     * @param doubles array of numbers
     */
    @Override
    public void offer(double[] doubles) {
        for (double x : doubles) {
            offer(x);
        }
    }

    /**
     * get an instance from array of numbers
     */
    public static DistinctNumberOffered of(double[] doubles) {
        DistinctNumberOffered distinctNumberOffered= new DistinctNumberOffered();
        distinctNumberOffered.offer(doubles);
        return distinctNumberOffered;
    }
}
