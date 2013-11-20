package com.edinarobotics.zed;

import com.edinarobotics.utils.gamepad.Gamepad;
import com.edinarobotics.utils.joystick.ThreeAxisJoystick;
import com.edinarobotics.zed.commands.*;
import com.edinarobotics.zed.subsystems.Auger;
import com.edinarobotics.zed.subsystems.Conveyor;
import com.edinarobotics.zed.subsystems.Shooter;

/**
 * Controls handles creating the {@link Gamepad} objects used to control the
 * robot as well as binding the proper Commands to button actions.
 */
public class Controls {

    private static Controls instance;
    private static final double ONE_JOYSTICK_MAGNITUDE = 1;

    public final ThreeAxisJoystick joystick;

    private Controls() {
        joystick = new ThreeAxisJoystick(1);
        
        // Augers
        joystick.TRIGGER.whenPressed(new AugerRotateCommand(Auger.AugerDirection.AUGER_DOWN));
        joystick.JOY_BOTTOM_LEFT.whenPressed(new AugerRotateCommand(Auger.AugerDirection.AUGER_DOWN));
        joystick.JOY_TOP_LEFT.whenPressed(new AugerRotateCommand(Auger.AugerDirection.AUGER_UP));
        joystick.BASE_INNER_MIDDLE.whenPressed(new SetAugerCommand(Auger.AugerDirection.AUGER_STOP));
        
        // Conveyor
        joystick.JOY_BOTTOM_RIGHT.whenPressed(new SetConveyorCommand(Conveyor.CONVEYOR_SHOOT_OUT));
        joystick.JOY_BOTTOM_RIGHT.whenReleased(new SetConveyorCommand(Conveyor.CONVEYOR_STOP));
        joystick.JOY_TOP_RIGHT.whenPressed(new SetConveyorCommand(Conveyor.CONVEYOR_SHOOT_IN));
        joystick.JOY_TOP_RIGHT.whenReleased(new SetConveyorCommand(Conveyor.CONVEYOR_STOP));
        joystick.BASE_OUTER_MIDDLE.whenPressed(new SetConveyorCommand(Conveyor.CONVEYOR_SHOOT_IN));
        joystick.BASE_OUTER_MIDDLE.whenReleased(new SetConveyorCommand(Conveyor.CONVEYOR_STOP));
        
        // Shooter
        joystick.BASE_OUTER_TOP.whileHeld(new SetShooterCommand(Shooter.SHOOTER_ON));
        joystick.BASE_OUTER_TOP.whenReleased(new SetShooterCommand(Shooter.SHOOTER_OFF));
    }

    /**
     * Returns the proper instance of Controls. This method creates a new
     * Controls object the first time it is called and returns that object for
     * each subsequent call.
     *
     * @return The current instance of Controls.
     */
    public static Controls getInstance() {
        if (instance == null) {
            instance = new Controls();
        }
        return instance;
    }
}
