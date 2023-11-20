package ca.jrvs.practice.codingChallenges;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class HelloWorldTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void isOddTest() {
        // Arrange
        HelloWorld oddTester = new HelloWorld();
        boolean expected = true;

        // Act
        boolean result = oddTester.isOdd(5);

        // Assert
        assertEquals(expected, result);
    }

    @Test
    public void isEvenTest() {
        // Arrange
        HelloWorld evenTester = new HelloWorld();
        boolean expected = true;

        // Act
        boolean result = evenTester.isEven(4);

        // Assert
        assertEquals(expected, result);
    }
}