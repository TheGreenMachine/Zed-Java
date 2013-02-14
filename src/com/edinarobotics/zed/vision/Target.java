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
    
    public String toString() {
        return "<Target " + x + ", " + y + ", " + distance + ", " + isCenter + ">";
    }

    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + (int) (Double.doubleToLongBits(this.x) ^ (Double.doubleToLongBits(this.x) >>> 32));
        hash = 47 * hash + (int) (Double.doubleToLongBits(this.y) ^ (Double.doubleToLongBits(this.y) >>> 32));
        hash = 47 * hash + (int) (Double.doubleToLongBits(this.distance) ^ (Double.doubleToLongBits(this.distance) >>> 32));
        hash = 47 * hash + (this.isCenter ? 1 : 0);
        return hash;
    }
    
    public boolean equals(Object other) {
        if(other instanceof Target) {
            return (this.x == (double)((Target)other).getX()) &&
                   (this.y == (double)((Target)other).getY()) &&
                   (this.distance == (double)((Target)other).getDistance()) &&
                   (this.isCenter == (boolean)((Target)other).isCenter());
        }
        return false;
    }
    
}
