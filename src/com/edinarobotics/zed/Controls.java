package com.edinarobotics.zed;

import com.edinarobotics.utils.gamepad.Gamepad;

/**
 * Controls handles creating the {@link Gamepad} objects
 * used to control the robot as well as binding the proper Commands
 * to button actions.
 */
public class Controls {
    private static Controls instance;
    
    public final Gamepad gamepad1;
    public final Gamepad gamepad2;
    
    private Controls(){
        gamepad1 = new Gamepad(1);
        gamepad2 = new Gamepad(2);
    }
    
    /**
     * Returns the proper instance of Controls.
     * This method creates a new Controls object the first time it is called
     * and returns that object for each subsequent call.
     * @return The current instance of Controls.
     */
    public static Controls getInstance(){
        if(instance == null){
            instance = new Controls();
        }
        return instance;
    }
}
