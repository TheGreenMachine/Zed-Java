package com.edinarobotics.zed.commands;

import com.edinarobotics.utils.gamepad.FilterSet;
import com.edinarobotics.utils.gamepad.filters.DeadzoneFilter;
import com.edinarobotics.utils.gamepad.filters.ScalingPowerFilter;
import com.edinarobotics.utils.joystick.JoystickResult;
import com.edinarobotics.utils.joystick.ThreeAxisJoystick;
import com.edinarobotics.zed.Components;
import com.edinarobotics.zed.subsystems.Drivetrain;
import com.edinarobotics.zed.subsystems.DrivetrainRotation;
import edu.wpi.first.wpilibj.command.Command;

/**
 * This {@link Command} allows a gamepad to control the {@link Drivetrain}. It
 * gets the current values from the gamepad and sends them to the drivetrain.
 */
public class GamepadDriveRotationCommand extends Command {

    private static final String COMMAND_NAME = "GamepadDriveRotation";
    private ThreeAxisJoystick joystick;
    private DrivetrainRotation drivetrainRotation;
    public static final double SPEED_MULTIPLIER = 1;

    public GamepadDriveRotationCommand(ThreeAxisJoystick joystick, FilterSet joystickFilters) {
        super(COMMAND_NAME);
        this.joystick = joystick;
        this.drivetrainRotation = Components.getInstance().drivetrainRotation;
        requires(drivetrainRotation);
    }

    /**
     * Constructs a new GamepadDriveCommand using the given gamepad and
     * drivetrain. This command will use a default set of filters.
     *
     * @param gamepad The Gamepad object to read for control values.
     * @param gamepad2 The second gamepad object to read for control values.
     */
    public GamepadDriveRotationCommand(ThreeAxisJoystick joystick) {
        super(COMMAND_NAME);
        this.joystick = joystick;
        this.drivetrainRotation = Components.getInstance().drivetrainRotation;
        requires(drivetrainRotation);
    }

    protected void initialize() {
    }

    /**
     * Submits values from the given {@code gamepad} to the given
     * {@code drivetrain}.
     */
    protected void execute() {
        //Precedence: gamepad then gamepad2
        double joyTwistVal = fixRange(joystick.getJoyTwist());
        drivetrainRotation.mecanumPolarRotate(SPEED_MULTIPLIER * joyTwistVal);
    }

    private double fixRange(double val) {
        if (Math.abs(val) < 0.4) {
            return 0.0;
        }

        if (signum(val) == 1) {
            return (val - 0.4) / (1.0 - 0.4) * (1.0 - 0.0) + 0.0;
        } else if (signum(val) == -1) {
            return (val - (-1.0)) / (-0.4 - (-1.0)) * (0.0 - (-1.0)) - 1.0;
        } else {
            return val;
        }
    }

    private byte signum(double value) {
        if (value < 0) {
            return -1;
        } else if (value > 0) {
            return 1;
        }
        return 0;
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
