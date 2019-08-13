package com.example.number;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.Assert.*;

/**
 * @author michael.malevannyy@gmail.com, 12.08.2019
 */

public class NumberOfferedTest {

    private NumberOffered numberOffered = new NumberOffered();
    private static final double epsilon = 10e-7;

    @Before
    public void before() {
        // initial state is undefined, e.g. NaN
        assertTrue(Double.isNaN(numberOffered.getMin()));
        assertTrue(Double.isNaN(numberOffered.getMax()));
        assertTrue(Double.isNaN(numberOffered.getAvg()));
    }


    @Test
    public void simpleTest() {

        numberOffered.offer(1);
        assertEquals(1, numberOffered.getMin(), 0);
        assertEquals(1, numberOffered.getMax(), 0);
        assertEquals(1, numberOffered.getAvg(), epsilon);

        numberOffered.offer(2);
        assertEquals(1, numberOffered.getMin(), 0);
        assertEquals(2, numberOffered.getMax(), 0);
        assertEquals(1.5, numberOffered.getAvg(), epsilon);

        numberOffered.offer(3);
        assertEquals(1, numberOffered.getMin(), 0);
        assertEquals(3, numberOffered.getMax(), 0);
        assertEquals(2, numberOffered.getAvg(), epsilon);

        numberOffered.offer(-6);
        assertEquals(-6, numberOffered.getMin(), 0);
        assertEquals(3, numberOffered.getMax(), 0);
        assertEquals(0, numberOffered.getAvg(), epsilon);
    }

    @Test(timeout = 1_000)
    public void randomTest() {
        int n = 1_000_000;
        double[] doubles = new Random().doubles(n).map(x -> 1/x).toArray();
        Arrays.stream(doubles).forEach(x -> numberOffered.offer(x));
        double avg = Arrays.stream(doubles).sum() / n;
        assertEquals(avg, numberOffered.getAvg(), epsilon);
    }

    @Test
    public void associativeTest() {
        double[] array1 = {1, 2, 3};
        double[] array2 = {100, 200, 300};

        double avg1 = NumberOffered.of(array1).getAvg();
        double avg2 = NumberOffered.of(array2).getAvg();
        assertEquals(2, avg1, epsilon);
        assertEquals(200, avg2, epsilon);

        double[] array3 = new double[array1.length+array2.length];
        System.arraycopy(array1, 0, array3, 0, array1.length);
        System.arraycopy(array2, 0, array3, array1.length, array2.length);

        double avg3 = NumberOffered.of(array3).getAvg();
        double avg3bis = NumberOffered.of(new double[]{avg1, avg2}).getAvg();

        assertEquals(avg3, avg3bis, epsilon);
    }

    @Test
    public void ofTest() {
        assertEquals(2, NumberOffered.of(new double[]{1, 2, 3}).getAvg(), epsilon);
    }

    @Test
    public void nanTest() {
        NumberOffered nanOffered = NumberOffered.of(new double[]{Double.NaN});
        assertTrue(Double.isNaN(nanOffered.getAvg()));
        assertTrue(Double.isNaN(nanOffered.getMin()));
        assertTrue(Double.isNaN(nanOffered.getMax()));

        nanOffered.offer(1);
        assertTrue(Double.isNaN(nanOffered.getAvg()));
        assertFalse(Double.isNaN(nanOffered.getMin()));
        assertFalse(Double.isNaN(nanOffered.getMax()));

        NumberOffered nanNotOffered = NumberOffered.of(new double[]{1});
        assertFalse(Double.isNaN(nanNotOffered.getAvg()));
    }

    @Test(expected = PrecisionException.class)
    public void precisionTest() {
        NumberOffered topOffered = NumberOffered.of(new double[]{Double.MAX_VALUE});
        assertEquals(Double.MAX_VALUE, topOffered.getAvg(), 0);
        topOffered.offer(-1);
    }

    @Test(expected = PrecisionException.class)
    public void precisionNegativeTest() {
        NumberOffered topOffered = NumberOffered.of(new double[]{-Double.MAX_VALUE});
        assertEquals(-Double.MAX_VALUE, topOffered.getAvg(), 0);
        topOffered.offer(1);
    }

    @Test(expected = PrecisionException.class)
    public void precisionInverseTest() {
        NumberOffered lowOffered = NumberOffered.of(new double[]{Double.MIN_VALUE});
        assertEquals(Double.MIN_VALUE, lowOffered.getAvg(), 0);
        lowOffered.offer(-1);
    }

    @Test
    public void infinityTest() {
        NumberOffered topOffered = NumberOffered.of(new double[]{Double.MAX_VALUE, Double.MAX_VALUE});
        assertEquals(Double.POSITIVE_INFINITY, topOffered.getAvg(), 0);
        assertEquals(Double.MAX_VALUE, topOffered.getMin(), 0);
        assertEquals(Double.MAX_VALUE, topOffered.getMax(), 0);
    }

    @Test
    public void fuzzyTest() {
        assertEquals(0, NumberOffered.of(new double[]{-Double.MAX_VALUE, Double.MAX_VALUE}).getAvg(), 0);
        assertEquals(0, NumberOffered.of(new double[]{-Double.MAX_VALUE, Double.MAX_VALUE,-Double.MAX_VALUE, Double.MAX_VALUE}).getAvg(), 0);
    }

}