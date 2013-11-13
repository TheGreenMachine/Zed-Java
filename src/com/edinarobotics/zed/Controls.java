package com.edinarobotics.zed;

import com.edinarobotics.utils.gamepad.Gamepad;
import com.edinarobotics.utils.gamepad.TwoAxisJoystick;
import com.edinarobotics.zed.commands.*;
import com.edinarobotics.zed.subsystems.Auger;
import com.edinarobotics.zed.subsystems.Conveyor;
import com.edinarobotics.zed.subsystems.Lifter;
import com.edinarobotics.zed.subsystems.Shooter;
import com.edinarobotics.zed.vision.TargetType;

/**
 * Controls handles creating the {@link Gamepad} objects used to control the
 * robot as well as binding the proper Commands to button actions.
 */
public class Controls {

    private static Controls instance;
    private static final double ONE_JOYSTICK_MAGNITUDE = 1;

    public final TwoAxisJoystick joystick1;
    public final TwoAxisJoystick joystick2;

    private Controls() {
        joystick1 = new TwoAxisJoystick(1);
        //Shooter
        joystick1.TRIGGER.whileHeld(new SetShooterCommand(Shooter.SHOOTER_ON));
        joystick1.TRIGGER.whenReleased(new SetShooterCommand(Shooter.SHOOTER_OFF));
        
        //Lifter
        joystick1.BUTTON_2.whileHeld(new SetLifterCommand(Lifter.LIFTER_DOWN));
        joystick1.BUTTON_2.whenReleased(new SetLifterCommand(Lifter.LIFTER_STOP));
        joystick1.BUTTON_3.whileHeld(new SetLifterCommand(Lifter.LIFTER_UP));
        joystick1.BUTTON_3.whenReleased(new SetLifterCommand(Lifter.LIFTER_STOP));
        
        joystick2 = new TwoAxisJoystick(2);
        //Conveyor
        joystick2.TRIGGER.whenPressed(new SetConveyorCommand(Conveyor.CONVEYOR_SHOOT_IN));
        joystick2.TRIGGER.whenReleased(new SetConveyorCommand(Conveyor.CONVEYOR_STOP));
        joystick2.BUTTON_4.whenPressed(new SetConveyorCommand(Conveyor.CONVEYOR_SHOOT_OUT));
        joystick2.BUTTON_4.whenReleased(new SetConveyorCommand(Conveyor.CONVEYOR_STOP));
        //Augers
        joystick2.BUTTON_3.whenPressed(new AugerRotateCommand(Auger.AugerDirection.AUGER_DOWN));
        joystick2.BUTTON_5.whenPressed(new AugerRotateCommand(Auger.AugerDirection.AUGER_UP));
        //joystick2.MIDDLE_LEFT.whenPressed(new SetAugerCommand(Auger.AugerDirection.AUGER_STOP));
        //FIX ME
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
