package com.edinarobotics.zed.vision;

public class Target {
    private double x;
    private double y;
    private double distance;
    private boolean isCenter;
    
    public Target(double x, double y, double distance, boolean isCenter) {
        this.x = x;
        this.y = y;
        this.distance = distance;
        this.isCenter = isCenter;
    }
    
    public double getX() {
        return x;
    }
    
    public double getY() {
        return y;
    }
    
    public double getDistance() {
        return distance;
    }
    
    public boolean isCenter() {
        return isCenter;
    }
    
}
