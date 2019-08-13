package com.example.number;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author michael.malevannyy@gmail.com, 12.08.2019
 */
public class DistinctNumberOfferedTest {
    private static final double epsilon = 10e-7;

    @Test
    public void distinctTest() {
        double[] unical = {1, 2, 3};
        DistinctNumberOffered distinct = DistinctNumberOffered.of(unical);
        assertEquals(1, distinct.getMin(), epsilon);
        assertEquals(2, distinct.getAvg(), epsilon);
        assertEquals(3, distinct.getMax(), epsilon);
        assertEquals(DistinctNumberOffered.of(unical).getAvg(), NumberOffered.of(unical).getAvg(), 0.0);

        double[] nonUnical = {1, 2, 3, 3, 3, 3};
        DistinctNumberOffered nonDistinct = DistinctNumberOffered.of(nonUnical);
        assertEquals(1, nonDistinct.getMin(), epsilon);
        assertEquals(2, nonDistinct.getAvg(), epsilon);
        assertEquals(3, nonDistinct.getMax(), epsilon);

        assertTrue(DistinctNumberOffered.of(nonUnical).getAvg() != NumberOffered.of(nonUnical).getAvg());
    }
}