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
 * This {@link Command} allows a gamepad to control the {@link Drivetrain}.
 * It gets the current values from the gamepad and sends them to the drivetrain.
 */
public class GamepadDriveRotationCommand extends Command{
    private static final String COMMAND_NAME = "GamepadDriveRotation";
    private ThreeAxisJoystick joystick;
    private FilterSet joystickFilters;
    private DrivetrainRotation drivetrainRotation;
    
    private static double ZERO_THRESHOLD = 0.05;
    
    public static final double SPEED_MULTIPLIER = 1;
    
    public GamepadDriveRotationCommand(ThreeAxisJoystick joystick, FilterSet joystickFilters){
        super(COMMAND_NAME);
        this.joystick = joystick;
        this.joystickFilters = joystickFilters;
        this.drivetrainRotation = Components.getInstance().drivetrainRotation;
        requires(drivetrainRotation);
    }
    
    /**
     * Constructs a new GamepadDriveCommand using the given gamepad and
     * drivetrain. This command will use a default set of filters.
     * @param gamepad The Gamepad object to read for control values.
     * @param gamepad2 The second gamepad object to read for control values.
     */
    public GamepadDriveRotationCommand(ThreeAxisJoystick joystick){
        super(COMMAND_NAME);
        joystickFilters = new FilterSet();
        joystickFilters.addFilter(new DeadzoneFilter(0.15));
        joystickFilters.addFilter(new ScalingPowerFilter(2));
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
        JoystickResult joystickState = joystickFilters.filter(joystick.getJoysticks());
        //Precedence: gamepad then gamepad2
        double joystickValue = joystickState.getJoyTwist();
        drivetrainRotation.mecanumPolarRotate(SPEED_MULTIPLIER*joystickValue);
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        
    }

    protected void interrupted() {
        
    }
}
