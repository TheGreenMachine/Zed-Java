package com.edinarobotics.zed.commands;

import com.edinarobotics.zed.Components;
import com.edinarobotics.zed.subsystems.DrivetrainRotation;
import edu.wpi.first.wpilibj.command.Command;

public class RotateXDegreesCommand extends Command {
    private DrivetrainRotation drivetrainRotation;
    private double degrees;
    
    private final double TIMEOUT_PER_DEGREE = 5;
    private final double ROTATION_POWER = 0.75;
    
    public RotateXDegreesCommand(double degrees) {
        super("RotateXDegrees");
        this.drivetrainRotation = Components.getInstance().drivetrainRotation;
        this.degrees = degrees;
        setTimeout(degrees * TIMEOUT_PER_DEGREE);
        requires(drivetrainRotation);
    }
    
    protected void initialize() {
        drivetrainRotation.resetGyro();
    }

    protected void execute() {
        drivetrainRotation.mecanumPolarRotate(signum(degrees) * ROTATION_POWER);
    }

    protected boolean isFinished() {
        return isTimedOut() || isOnTarget();
    }
    
    private boolean isOnTarget() {
        return Math.abs(drivetrainRotation.getGyroAngle()) >= Math.abs(degrees);
    }
    
    private byte signum(double number) {
        if(number > 0) {
            return 1;
        } else if(number < 0) {
            return -1;
        } else {
            return 0;
        }
    }

    protected void end() {
        drivetrainRotation.mecanumPolarRotate(0);
    }

    protected void interrupted() {
        end();
    }
}
