package com.edinarobotics.utils.gamepad.filters;

import com.edinarobotics.utils.gamepad.GamepadFilter;
import com.edinarobotics.utils.gamepad.GamepadResult;
import com.edinarobotics.utils.joystick.JoystickResult;

/**
 * This filter can be used to apply a deadzone to all the axes of a Gamepad.
 */
public class DeadzoneFilter implements GamepadFilter{
    private double deadzone;
    /**
     * Constructs a DeadzonFilter with the specified radius.
     * @param radius the radius of the deadzone (inclusive).
     */
    public DeadzoneFilter(double radius){
        deadzone = radius;
    }
    
    /**
     * Performs the deadzone filtering.
     * @param toFilter the input GamepadResult.
     * @return the deadzoned GamepadResult.
     */
    public GamepadResult filter(GamepadResult toFilter){
        double lx = deadzone(toFilter.getLeftX());
        double ly = deadzone(toFilter.getLeftY());
        double rx = deadzone(toFilter.getRightX());
        double ry = deadzone(toFilter.getRightY());
        return new GamepadResult(lx,ly,rx,ry);
    }

    public JoystickResult filter(JoystickResult toFilter) {
        double x = deadzone(toFilter.getJoyX());
        double y = deadzone(toFilter.getJoyY());
        double twist = deadzone(toFilter.getJoyTwist());
        return new JoystickResult(x, y, twist);
    }
    
    private double deadzone(double value){
        if(Math.abs(value)<=deadzone){
            return 0;
        }
        return value;
    }
}
