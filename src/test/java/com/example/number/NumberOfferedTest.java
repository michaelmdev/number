package com.example.number;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author michael.malevannyy@gmail.com, 12.08.2019
 */

public class NumberOfferedTest {

    private NumberOffered numberOffered = new NumberOffered();
    private static final double epsilon = 10e-7;

    @Before
    public void before() {
        // initial state is undefined, e.g. NaN
        assertTrue(Double.isNaN(numberOffered.getSmallest()));
        assertTrue(Double.isNaN(numberOffered.getLargest()));
        assertTrue(Double.isNaN(numberOffered.getAvg()));
    }


    @Test
    public void offerTestSimple() {

        numberOffered.offer(1);
        assertEquals(1, numberOffered.getSmallest(), 0);
        assertEquals(1, numberOffered.getLargest(), 0);
        assertEquals(1, numberOffered.getAvg(), epsilon);

        numberOffered.offer(2);
        assertEquals(1, numberOffered.getSmallest(), 0);
        assertEquals(2, numberOffered.getLargest(), 0);
        assertEquals(1.5, numberOffered.getAvg(), epsilon);

        numberOffered.offer(3);
        assertEquals(1, numberOffered.getSmallest(), 0);
        assertEquals(3, numberOffered.getLargest(), 0);
        assertEquals(2, numberOffered.getAvg(), epsilon);

        numberOffered.offer(-6);
        assertEquals(-6, numberOffered.getSmallest(), 0);
        assertEquals(3, numberOffered.getLargest(), 0);
        assertEquals(0, numberOffered.getAvg(), epsilon);
    }

    @Test
    public void offerTestInducting() {

        int n = 1000000;
        double[] doubles = new Random().doubles(n).toArray();
        Arrays.stream(doubles).forEach(x ->  numberOffered.offer(x));
        double avg = Arrays.stream(doubles).sum()/n;
        assertEquals(avg, numberOffered.getAvg(), epsilon);

    }
}