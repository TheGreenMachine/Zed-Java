package com.edinarobotics.zed.vision;

import edu.wpi.first.wpilibj.PIDSource;

public class TargetYPIDSource implements PIDSource {
    private double targetY;
    
    /**
     * Gets the targetX for the PIDController.
     * @return targetX
     */
    public double pidGet() {
        return targetY;
    }
    
    public void setTarget(Target target) {
        this.targetY = target.getY();
    }
}
