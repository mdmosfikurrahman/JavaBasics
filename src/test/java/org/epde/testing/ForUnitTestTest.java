package org.epde.testing;

import org.junit.jupiter.api.Test;

import java.util.*;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;

/**
 * This is the Unit Test file for ForUnitTest.java
 * In this file I have used JUnit5 and some assertions' method. These are:
 * <ul>
 * <li><b>assertEquals</b>: Asserts that two objects are equal. In the code, this method is used to verify that the result of an operation is equal to the expected value.
 * <li><b>assertNotEquals</b>: Asserts that two objects are not equal. In the code, this method is used to verify that the result of an operation is not equal to the expected value.
 * <li><b>assertSame</b>: Asserts that two objects refer to the same object. In the code, this method is used to verify that two objects are the same instance.
 * <li><b>assertNotSame</b>: Asserts that two objects do not refer to the same object. In the code, this method is used to verify that two objects are not the same instance.
 * <li><b>assertNull</b>: Asserts that an object is null. In the code, this method is used to verify that the result of a method call with a null argument returns null.
 * <li><b>assertNotNull</b>: Asserts that an object is not null. In the code, this method is used to verify that the result of a method call with a non-null argument is not null.
 * <li><b>assertTrue</b>: Asserts that a boolean condition is true. In the code, this method is used to verify that a boolean condition is true.
 * <li><b>assertFalse</b>: Asserts that a boolean condition is false. In the code, this method is used to verify that a boolean condition is false.
 * <li><b>assertThrows</b>: Asserts that an exception of the specified type is thrown. In the code, this method is used to verify that a method call throws an expected exception.
 * <li><b>assertAll</b>: Groups multiple assertions together. In the code, this method is used to group multiple assertions related to a single method.
 * </ul>
 */

public class ForUnitTestTest {

    @Test
    void testAdd() {
        ForUnitTest calculator = new ForUnitTest();

        int result = calculator.add(2, 3);
        assertEquals(5, result);
        int result2 = calculator.add(2, 5);
        assertNotEquals(5, result2);
    }

    @Test
    void testSubtract() {
        ForUnitTest calculator = new ForUnitTest();

        int result = calculator.subtract(5, 3);
        assertEquals(2, result);
    }

    @Test
    void testMultiply() {
        ForUnitTest calculator = new ForUnitTest();

        int result = calculator.multiply(2, 3);
        assertEquals(6, result);
    }

    @Test
    void testDivide() {
        ForUnitTest calculator = new ForUnitTest();

        int result = calculator.divide(6, 3);
        assertEquals(2, result);

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> calculator.divide(6, 0));
        assertEquals("Cannot divide by zero", exception.getMessage());

        try {
            calculator.divide(10, 0);
            fail("Expected IllegalArgumentException was not thrown");
        } catch (IllegalArgumentException e) {
            // expected exception was thrown, test passes
        }
    }


    @Test
    void testEven() {
        ForUnitTest booleanCheck = new ForUnitTest();
        assertTrue(booleanCheck.isEven(2));
        assertTrue(booleanCheck.isEven(-4));
        assertFalse(booleanCheck.isEven(3));
        assertFalse(booleanCheck.isEven(-7));
    }


    @Test
    void testToUpperCaseWithNull() {
        String result = ForUnitTest.toUpperCase(null);
        assertNull(result);
    }

    @Test
    void testToUpperCaseWithNonNull() {
        String result = ForUnitTest.toUpperCase("hello");
        assertNotNull(result);
    }

    @Test
    void testCreateObject() {
        Object obj1 = ForUnitTest.createObject();
        Object obj2 = ForUnitTest.createObject();

        // Test that obj1 and obj2 are not the same object
        assertNotSame(obj1, obj2);

        // Test that obj1 and obj1 are the same object
        assertSame(obj1, obj1);
    }

    @Test
    void testIsPrime() {
        ForUnitTest primeCheck = new ForUnitTest();
        assertAll("primeCheck.isPrime",
                () -> assertFalse(primeCheck.isPrime(-1)),
                () -> assertFalse(primeCheck.isPrime(0)),
                () -> assertFalse(primeCheck.isPrime(1)),
                () -> assertTrue(primeCheck.isPrime(2)),
                () -> assertTrue(primeCheck.isPrime(3)),
                () -> assertFalse(primeCheck.isPrime(4)),
                () -> assertTrue(primeCheck.isPrime(5)),
                () -> assertFalse(primeCheck.isPrime(6)),
                () -> assertTrue(primeCheck.isPrime(7)),
                () -> assertFalse(primeCheck.isPrime(8)),
                () -> assertFalse(primeCheck.isPrime(9)),
                () -> assertFalse(primeCheck.isPrime(10)));
    }

    @Test
    void givenTwoLists_whenAssertingIterables_thenEquals() {
        Iterable<String> firstList = new ArrayList<>(asList("Java", "Junit", "Test"));
        Iterable<String> secondList = new LinkedList<>(asList("Java", "Junit", "Test"));

        assertIterableEquals(firstList, secondList);
    }

    @Test
    void testBubbleSort() {
        ForUnitTest test = new ForUnitTest();
        int[] arr = {5, 1, 4, 2, 8};
        int[] expected = {1, 2, 4, 5, 8};
        int[] result = test.bubbleSort(arr);
        assertArrayEquals(expected, result);
    }

}