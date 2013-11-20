package com.edinarobotics.utils.joystick;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class represents a Logitech Extreme 3D Pro joystick.
 */
public class ThreeAxisJoystick {

    private Joystick joystick;
    public final Button TRIGGER;
    public final Button JOY_THUMB;
    public final Button JOY_BOTTOM_LEFT;
    public final Button JOY_BOTTOM_RIGHT;
    public final Button JOY_TOP_LEFT;
    public final Button JOY_TOP_RIGHT;
    public final Button BASE_INNER_TOP;
    public final Button BASE_INNER_MIDDLE;
    public final Button BASE_INNER_BOTTOM;
    public final Button BASE_OUTER_TOP;
    public final Button BASE_OUTER_MIDDLE;
    public final Button BASE_OUTER_BOTTOM;

    public ThreeAxisJoystick(int port) {
        joystick = new Joystick(port);
        TRIGGER = new JoystickButton(joystick, 1);
        JOY_THUMB = new JoystickButton(joystick, 2);
        JOY_BOTTOM_LEFT = new JoystickButton(joystick, 3);
        JOY_BOTTOM_RIGHT = new JoystickButton(joystick, 4);
        JOY_TOP_LEFT = new JoystickButton(joystick, 5);
        JOY_TOP_RIGHT = new JoystickButton(joystick, 6);

        BASE_INNER_TOP = new JoystickButton(joystick, 8);
        BASE_INNER_MIDDLE = new JoystickButton(joystick, 10);
        BASE_INNER_BOTTOM = new JoystickButton(joystick, 12);
        BASE_OUTER_TOP = new JoystickButton(joystick, 7);
        BASE_OUTER_MIDDLE = new JoystickButton(joystick, 9);
        BASE_OUTER_BOTTOM = new JoystickButton(joystick, 11);
    }
    
    public double getJoyX() {
        return joystick.getRawAxis(1);
    }
    
    public double getJoyY() {
        return -joystick.getRawAxis(2);
    }
    
    public double getJoyTwist() {
        return joystick.getRawAxis(3);
    }
    
    public double getThrottle() {
        return joystick.getRawAxis(4);
    }
    
    public double getHatX() {
        return joystick.getRawAxis(5);
    }
    
    public double getHatY() {
        return -joystick.getRawAxis(6);
    }
    
    public JoystickResult getJoysticks(){
        return new JoystickResult(getJoyX(), getJoyY(), getJoyTwist());
    }
}
