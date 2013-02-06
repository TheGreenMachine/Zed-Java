package com.edinarobotics.zed.vision;

import com.edinarobotics.zed.subsystems.Lifter;

public class LifterTargetY {
    private Target target;
    private double ySetpoint;
    private double yTolerance;
    private boolean isOnTarget = false;
    
    public void setTarget(Target target) {
        this.target = target;
    }
    
    public void setYSetpoint(double ySetpoint) {
        this.ySetpoint = ySetpoint;
    }
    
    public void setYTolerance(double yTolerance) {
        this.yTolerance = yTolerance;
    }
    
    public Lifter.LifterDirection targetY() {
        if(target.getY() > (ySetpoint + yTolerance)) {
            return Lifter.LifterDirection.LIFTER_UP;
        }
        else if(target.getY() < (ySetpoint - yTolerance)) {
            return Lifter.LifterDirection.LIFTER_DOWN;
        }
        else {
            return Lifter.LifterDirection.LIFTER_STOP;
        }
    }
    
    public boolean onTarget() {
        if(target.getY() > (ySetpoint + yTolerance)) {
            isOnTarget = false;
        }
        else if(target.getY() < (ySetpoint - yTolerance)) {
            isOnTarget = false;
        }
        else {
            isOnTarget = true;
        }
        return isOnTarget;
    }
    
}
