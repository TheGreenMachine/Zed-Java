package com.edinarobotics.utils.gamepad;

import com.edinarobotics.utils.joystick.JoystickResult;

/**
 * Used to filter the joystick values of a Gamepad.
 */
public interface GamepadFilter {
    public GamepadResult filter(GamepadResult toFilter);
    public JoystickResult filter(JoystickResult toFilter);
}
