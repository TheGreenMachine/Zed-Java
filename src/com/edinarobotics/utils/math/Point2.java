package com.edinarobotics.utils.math;

/**
 * This class implements a coordinate point on a plane, defined by the x
 * and y unit distance from the origin.
 */
public class Point2 {
    private double x;
    private double y;
    
    /**
     * Creates a two-dimensional point based on the x and y values described.
     * @param x The x value of the point
     * @param y The y value of the point
     */
    public Point2(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    /**
     * Returns the x coordinate of the point
     * @return The x coordinate
     */
    public double getX() {
        return x;
    }
    
    /**
     * Returns the y coordinate of the point
     * @return The y coordinate
     */
    public double getY() {
        return y;
    }
}
