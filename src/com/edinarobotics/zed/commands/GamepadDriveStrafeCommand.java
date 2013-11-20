package com.edinarobotics.zed.commands;

import com.edinarobotics.utils.gamepad.FilterSet;
import com.edinarobotics.utils.joystick.ThreeAxisJoystick;
import com.edinarobotics.utils.gamepad.filters.DeadzoneFilter;
import com.edinarobotics.utils.gamepad.filters.ScalingPowerFilter;
import com.edinarobotics.utils.joystick.JoystickResult;
import com.edinarobotics.zed.Components;
import com.edinarobotics.zed.subsystems.Drivetrain;
import com.edinarobotics.zed.subsystems.DrivetrainStrafe;
import edu.wpi.first.wpilibj.command.Command;

/**
 * This {@link Command} allows a gamepad to control the {@link Drivetrain}.
 * It gets the current values from the gamepad and sends them to the drivetrain.
 */
public class GamepadDriveStrafeCommand extends Command{
    private static final String COMMAND_NAME = "GamepadDriveStrafe";
    private static final double SPEED_MULTIPLIER = 1;
    
    public static boolean reverseStrafe = false;
    
    ThreeAxisJoystick joystick;
    FilterSet filters;
    DrivetrainStrafe drivetrainStrafe;
    
    /**
     * Construct a new GamepadDriveCommand using the given gamepad, filters
     * and drivetrain.
     * @param joystick The ThreeAxisJoystick object to read for control values.
     * @param filters The set of filters to use when filtering gamepad output.
     * @param drivetrain The drivetrain object to control.
     */
    public GamepadDriveStrafeCommand(ThreeAxisJoystick joystick, FilterSet filters){
        super(COMMAND_NAME);
        this.joystick = joystick;
        this.filters = filters;
        this.drivetrainStrafe = Components.getInstance().drivetrainStrafe;
        requires(drivetrainStrafe);
    }
    
    /**
     * Constructs a new GamepadDriveCommand using the given gamepad and
     * drivetrain. This command will use a default set of filters.
     * @param joystick The ThreeAxisJoystick object to read for control values.
     * @param drivetrain The drivetrain object to control.
     */
    public GamepadDriveStrafeCommand(ThreeAxisJoystick joystick){
        super(COMMAND_NAME);
        filters = new FilterSet();
        filters.addFilter(new DeadzoneFilter(0.15));
        this.joystick = joystick;
        this.drivetrainStrafe = Components.getInstance().drivetrainStrafe;
        requires(drivetrainStrafe);
    }

    protected void initialize() {
    }

    /**
     * Submits values from the given {@code gamepad} to the given
     * {@code drivetrain}.
     */
    protected void execute() {
        JoystickResult joystickState = filters.filter(joystick.getJoysticks());
        drivetrainStrafe.mecanumPolarStrafe(SPEED_MULTIPLIER*joystickState.getJoyMagnitude(), joystickState.getJoyDirection()+(reverseStrafe ? 180 : 0));
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        
    }

    protected void interrupted() {
        end();
    }
}
