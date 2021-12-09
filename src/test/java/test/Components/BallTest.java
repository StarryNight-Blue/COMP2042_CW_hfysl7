package test.Components;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BallTest {

    private final Point2D Center = new Point(20, 20);
    private final Color Inner = new Color(2, 2, 2, 2);
    private final Color Border = new Color(1, 1, 1, 1);

    private Ball ballTest;


    @BeforeEach
    void setUp() {
        ballTest = new Ball(Center, 10, 10, Inner, Border) {
            @Override
            protected Shape makeBall(Point2D center, int radiusA, int radiusB) {
                double x = center.getX() - (radiusA * 0.5);
                double y = center.getY() - (radiusB * 0.5);

                return new Ellipse2D.Double(x, y, radiusA, radiusB);
            }
        };
    }
    @Test
    void moveTest() {
        ballTest.setCenter(new Point(0,0));
        ballTest.setSpeed(2,4);

        //to call the function that you want to test
        ballTest.move();

        //write what you expect to come out
        assertEquals(2, ballTest.getCenter().getX());
        assertEquals(4, ballTest.getCenter().getY());

    }

    @Test
    void testSetSpeed() {
        // Setup
        // Run the test
        ballTest.setSpeed(0, 0);

        // Verify the results
    }

    @Test
    void testSetXSpeed() {
        // Setup
        // Run the test
        ballTest.setXSpeed(0);

        // Verify the results
    }

    @Test
    void testSetYSpeed() {
        // Setup
        // Run the test
        ballTest.setYSpeed(0);

        // Verify the results
    }

    @Test
    void testReverseX() {
        // Setup
        // Run the test
        ballTest.reverseX();

        // Verify the results
    }

    @Test
    void testReverseY() {
        // Setup
        // Run the test
        ballTest.reverseY();

        // Verify the results
    }

    @Test
    void testGetBorderColor() {
        // Setup
        final Color expectedResult = new Color(0, 0, 0, 0);

        // Run the test
        final Color result = ballTest.getBorderColor();

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testGetInnerColor() {
        // Setup
        final Color expectedResult = new Color(0, 0, 0, 0);

        // Run the test
        final Color result = ballTest.getInnerColor();

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testGetPosition() {
        // Setup
        final Point2D expectedResult = null;

        // Run the test
        final Point2D result = ballTest.getPosition();

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testMoveTo() {
        // Setup
        final Point p = new Point(0, 0);


        // Run the test
        ballTest.moveTo(p);

        // Verify the results
    }
}