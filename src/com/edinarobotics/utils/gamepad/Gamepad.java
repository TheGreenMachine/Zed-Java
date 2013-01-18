package com.edinarobotics.utils.gamepad;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * Represents a gamepad with two joysticks and several buttons.
 * Makes interacting with a gamepad easier.
 * Returns results through a GamepadResult.
 */
public class Gamepad extends Joystick{
    public static final int LEFT_X_AXIS = 1;
    public static final int LEFT_Y_AXIS = 2;
    public static final int RIGHT_X_AXIS = 3;
    public static final int RIGHT_Y_AXIS = 4;
    
    public static final int DPAD_X = 5;
    public static final int DPAD_Y = 6;
    
    public static final int LEFT_BUMPER = 5;
    public static final int LEFT_TRIGGER = 7;
    public static final int RIGHT_BUMPER = 6;
    public static final int RIGHT_TRIGGER = 8;
    public static final int BUTTON_1 = 1;
    public static final int BUTTON_2 = 2;
    public static final int BUTTON_3 = 3;
    public static final int BUTTON_4 = 4;
    public static final int BUTTON_9 = 9;
    public static final int BUTTON_10 = 10;
    
    public final JoystickButton LEFT_BUMPER_BUTTON;
    public final JoystickButton LEFT_TRIGGER_BUTTON;
    public final JoystickButton RIGHT_BUMPER_BUTTON;
    public final JoystickButton RIGHT_TRIGGER_BUTTON;
    public final JoystickButton BUTTON_1_BUTTON;
    public final JoystickButton BUTTON_2_BUTTON;
    public final JoystickButton BUTTON_3_BUTTON;
    public final JoystickButton BUTTON_4_BUTTON;
    public final JoystickButton BUTTON_9_BUTTON;
    public final JoystickButton BUTTON_10_BUTTON;
    public final JoystickButton LEFT_JOYSTICK_BUTTON_BUTTON;
    public final JoystickButton RIGHT_JOYSTICK_BUTTON_BUTTON;
    
    public static final int LEFT_JOYSTICK_BUTTON = 11;
    public static final int RIGHT_JOYSTICK_BUTTON = 12;
                    
    private static final double DPAD_THRESHOLD = 0.9;
    
    public Gamepad(int port){
        super(port);
        LEFT_BUMPER_BUTTON = new JoystickButton(this, LEFT_BUMPER);
        LEFT_TRIGGER_BUTTON = new JoystickButton(this, LEFT_TRIGGER);
        RIGHT_BUMPER_BUTTON = new JoystickButton(this, RIGHT_BUMPER);
        RIGHT_TRIGGER_BUTTON = new JoystickButton(this, RIGHT_TRIGGER);
        BUTTON_1_BUTTON = new JoystickButton(this, BUTTON_1);
        BUTTON_2_BUTTON = new JoystickButton(this, BUTTON_2);
        BUTTON_3_BUTTON = new JoystickButton(this, BUTTON_3);
        BUTTON_4_BUTTON = new JoystickButton(this, BUTTON_4);
        BUTTON_9_BUTTON = new JoystickButton(this, BUTTON_9);
        BUTTON_10_BUTTON = new JoystickButton(this, BUTTON_10);
        LEFT_JOYSTICK_BUTTON_BUTTON = new JoystickButton(this, LEFT_JOYSTICK_BUTTON);
        RIGHT_JOYSTICK_BUTTON_BUTTON = new JoystickButton(this, RIGHT_JOYSTICK_BUTTON);
    }
    
    public double getLeftX(){
        return this.getRawAxis(LEFT_X_AXIS);
    }
    
    public double getLeftY(){
        return this.getRawAxis(LEFT_Y_AXIS);
    }
    
    public double getRightX(){
        return this.getRawAxis(RIGHT_X_AXIS);
    }
    
    public double getRightY(){
        return this.getRawAxis(RIGHT_Y_AXIS);
    }
    
    public int getDPadX()
    {
        return dPadToInt(this.getRawAxis(DPAD_X));
    }
    
    public int getDPadY()
    {
        return dPadToInt(this.getRawAxis(DPAD_Y));
    }
    
    private int dPadToInt(double value){
        if(value >= DPAD_THRESHOLD){
            return 1;
        }
        else if(value <= -DPAD_THRESHOLD){
            return -1;
        }
        return 0;
    }
    
    public GamepadResult getJoysticks(){
        return new GamepadResult(getLeftX(),getLeftY(),getRightX(),getRightY());
    }

}
