package com.example.number;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;

import static java.lang.Double.NaN;

/**
 * At any given time it can tell 3 things:
 * 1) the smallest number it has encountered so far
 * 2) the largest number it has encountered so far
 * 3) the average of all numbers it has encountered so far
 * <p>
 * Prove that it is working correctly.
 * Make it so that a novice programmer cannot use it the wrong way,
 * nor that an evil programmer can break it.
 *
 * @author michael.malevannyy@gmail.com, 12.08.2019
 */
public class NumberOffered {

    private double smallest = NaN;
    private double largest = NaN;

    private double sum = 0;

    private double avg = NaN;

    private int counter = 0;

    public void offer(double x) {
        synchronized (this) {
            if (Double.isNaN(smallest) || x < smallest)
                smallest = x;
            if (Double.isNaN(largest) || x > largest)
                largest = x;

            sum += x;

            avg = sum / ++counter;
        }
    }

    public void offerAll(@NotNull Collection<Double> doubles) {
        doubles.stream().filter(aDouble -> aDouble != null && !aDouble.isNaN()).forEach(this::offer);
    }

    public double getSmallest() {
        return smallest;
    }

    public double getLargest() {
        return largest;
    }

    public double getAvg() {
        return avg;
    }
}
