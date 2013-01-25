package com.edinarobotics.zed.vision;

import edu.wpi.first.wpilibj.PIDSource;

public class PIDTargetX implements PIDSource {
    private double targetX;
    
    /**
     * Gets the targetX for the PIDController.
     * @return targetX
     */
    public double pidGet() {
        return targetX;
    }
    
    public void setTarget(Target target) {
        this.targetX = target.getX();
    }
}
