package com.edinarobotics.zed.commands;

import com.edinarobotics.utils.pid.PIDConfig;
import com.edinarobotics.utils.pid.PIDTuningManager;
import com.edinarobotics.zed.Components;
import com.edinarobotics.zed.subsystems.DrivetrainRotation;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Command;

public class RotateXDegreesCommand extends Command {
    private DrivetrainRotation drivetrainRotation;
    private PIDController pidControllerDegrees;
    private PIDConfig pidConfigDegrees;
    private double degrees;
    
    private final double P = 1;
    private final double I = 0;
    private final double D = 0;
    private final double TIMEOUT_PER_DEGREE = 5;
    private final double TOLERANCE = 5;
    
    public RotateXDegreesCommand(double degrees) {
        super("RotateXDegrees");
        this.drivetrainRotation = Components.getInstance().drivetrainRotation;
        this.pidControllerDegrees = new PIDController(P, I, D, drivetrainRotation, drivetrainRotation);
        this.pidConfigDegrees = PIDTuningManager.getInstance().getPIDConfig("Rotate X Degrees");
        this.degrees = degrees;
        setTimeout(degrees * TIMEOUT_PER_DEGREE);
        pidControllerDegrees.setAbsoluteTolerance(TOLERANCE);
        requires(drivetrainRotation);
    }
    
    protected void initialize() {
        pidControllerDegrees.enable();
        pidControllerDegrees.setSetpoint(degrees);
    }

    protected void execute() {
        //PID tuning code
        pidControllerDegrees.setPID(pidConfigDegrees.getP(P), pidConfigDegrees.getI(I),
                pidConfigDegrees.getD(D));
        pidConfigDegrees.setSetpoint(pidControllerDegrees.getSetpoint());
        pidConfigDegrees.setValue(drivetrainRotation.pidGet());
    }

    protected boolean isFinished() {
        return isTimedOut() || pidControllerDegrees.onTarget();
    }

    protected void end() {
        pidControllerDegrees.disable();
        drivetrainRotation.mecanumPolarRotate(0);
    }

    protected void interrupted() {
        end();
    }
}
