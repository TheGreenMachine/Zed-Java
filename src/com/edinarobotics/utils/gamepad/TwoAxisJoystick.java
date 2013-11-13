package com.edinarobotics.utils.gamepad;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class represents a Logitech Attack 3 joystick.
 */
public class TwoAxisJoystick {
    private Joystick joystick;
    
    public final Button TRIGGER;
    public final Button BUTTON_1;
    public final Button BUTTON_2;
    public final Button BUTTON_3;
    public final Button BUTTON_4;
    public final Button BUTTON_5;
    
    public TwoAxisJoystick(int port) {
        joystick = new Joystick(port);
        TRIGGER = new JoystickButton(joystick, 1);
        BUTTON_1 = new JoystickButton(joystick, 2);
        BUTTON_2 = new JoystickButton(joystick, 3);
        BUTTON_3 = new JoystickButton(joystick, 4);
        BUTTON_4 = new JoystickButton(joystick, 5);
        BUTTON_5 = new JoystickButton(joystick, 6);
    }
    
    public double getAxisX() {
        return joystick.getRawAxis(1);
    }
    
    public double getAxisY() {
        return joystick.getRawAxis(2);
    }
}
