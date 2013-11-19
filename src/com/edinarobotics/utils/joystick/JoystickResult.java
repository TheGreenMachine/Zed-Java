package com.edinarobotics.utils.joystick;

import com.sun.squawk.util.MathUtils;

public class JoystickResult {
    private double x;
    private double y;
    private double twist;
    
    public JoystickResult(double x, double y, double twist) {
        this.x = x;
        this.y = y;
        this.twist = twist;
    }
    
    public double getJoyX(){
        return x;
    }
    
    public double getJoyY(){
        return y;
    }
    
    public double getJoyTwist(){
        return twist;
    }
    
    public double getJoyMagnitude(){
        return Math.sqrt(MathUtils.pow(getJoyX(), 2)+MathUtils.pow(getJoyY(), 2));
    }
    
    public double getJoyDirection(){
        return Math.toDegrees(MathUtils.atan2(getJoyX(), getJoyY()));
    }
}
