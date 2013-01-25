package com.edinarobotics.zed.vision;

import edu.wpi.first.wpilibj.PIDSource;

public class PIDTargetY implements PIDSource {
    private double targetY;
    
    /**
     * Gets the targetY for the PIDController.
     * @return targetY
     */
    public double pidGet() {
        return targetY;
    }
    
    public void setTarget(Target target) {
        this.targetY = target.getY();
    }
}
