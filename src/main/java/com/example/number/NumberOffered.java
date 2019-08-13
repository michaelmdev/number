package com.example.number;

import static java.lang.Double.NaN;

/**
 * At any given time it can tell 3 things:
 * 1) the min number it has encountered so far
 * 2) the max number it has encountered so far
 * 3) the average of all numbers it has encountered so far
 *
 * @author michael.malevannyy@gmail.com, 12.08.2019
 */
public class NumberOffered {

    private double min = NaN;
    private double max = NaN;

    private double sum = 0;

    private double avg = NaN;

    private long counter = 0;

    /**
     * offer a number
     *
     * @param x number
     */
    public void offer(double x) {
        synchronized (this) {
            if (Math.ulp(sum) > Math.abs(x))
                throw new PrecisionException();

            if (sum > 0 && Math.ulp(x) > Math.abs(sum))
                throw new PrecisionException();

            if (counter >= Long.MAX_VALUE)
                throw new OverflowException();

            if (Double.isNaN(min) || x < min)
                min = x;
            if (Double.isNaN(max) || x > max)
                max = x;

            sum += x;

            avg = sum / ++counter;
        }
    }

    /**
     * offer an array of numbers
     *
     * @param doubles array of numbers
     */
    public void offer(double[] doubles) {
        for (double x : doubles) {
            offer(x);
        }
    }

    /**
     * get an instance from array of numbers
     */
    public static NumberOffered of(double[] doubles) {
        NumberOffered numberOffered = new NumberOffered();
        numberOffered.offer(doubles);
        return numberOffered;
    }

    /**
     * smallest offered number
     */
    public double getMin() {
        return min;
    }

    /**
     * largest offered number
     */
    public double getMax() {
        return max;
    }

    /**
     * average of all numbers
     */
    public double getAvg() {
        return avg;
    }
}
