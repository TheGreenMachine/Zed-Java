package com.edinarobotics.zed.commands;

import com.edinarobotics.utils.gamepad.FilterSet;
import com.edinarobotics.utils.gamepad.Gamepad;
import com.edinarobotics.utils.gamepad.GamepadResult;
import com.edinarobotics.utils.gamepad.filters.DeadzoneFilter;
import com.edinarobotics.utils.gamepad.filters.ScalingFilter;
import com.edinarobotics.zed.subsystems.Drivetrain;
import edu.wpi.first.wpilibj.command.Command;

/**
 * This {@link Command} allows a gamepad to control the {@link Drivetrain}.
 * It gets the current values from the gamepad and sends them to the drivetrain.
 */
public class GamepadDriveCommand extends Command{
    private static final String COMMAND_NAME = "GamepadDrive";
    Gamepad gamepad;
    FilterSet filters;
    Drivetrain drivetrain;
    
    /**
     * Construct a new GamepadDriveCommand using the given gamepad, filters
     * and drivetrain.
     * @param gamepad The Gamepad object to read for control values.
     * @param filters The set of filters to use when filtering gamepad output.
     * @param drivetrain The drivetrain object to control.
     */
    public GamepadDriveCommand(Gamepad gamepad, FilterSet filters, Drivetrain drivetrain){
        super(COMMAND_NAME);
        this.gamepad = gamepad;
        this.filters = filters;
        this.drivetrain = drivetrain;
        requires(drivetrain);
    }
    
    /**
     * Constructs a new GamepadDriveCommand using the given gamepad and
     * drivetrain. This command will use a default set of filters.
     * @param gamepad The Gamepad object to read for control values.
     * @param drivetrain The drivetrain object to control.
     */
    public GamepadDriveCommand(Gamepad gamepad, Drivetrain drivetrain){
        super(COMMAND_NAME);
        filters = new FilterSet();
        filters.addFilter(new DeadzoneFilter(0.15));
        filters.addFilter(new ScalingFilter());
        this.gamepad = gamepad;
        this.drivetrain = drivetrain;
        requires(drivetrain);
    }

    protected void initialize() {
        
    }

    /**
     * Submits values from the given {@code gamepad} to the given
     * {@code drivetrain}.
     */
    protected void execute() {
        GamepadResult gamepadState = filters.filter(gamepad.getJoysticks());
        drivetrain.mecanumPolar(gamepadState.getLeftMagnitude(),
                gamepadState.getLeftDirection(), 
                gamepadState.getRightX());
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        
    }

    protected void interrupted() {
        
    }
}
