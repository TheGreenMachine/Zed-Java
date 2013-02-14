package com.edinarobotics.zed.commands;

import com.edinarobotics.zed.Components;
import com.edinarobotics.zed.subsystems.Drivetrain;
import com.edinarobotics.zed.subsystems.DrivetrainRotation;
import com.edinarobotics.zed.subsystems.DrivetrainStrafe;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class DriveForXDistanceCommand extends Command {
    private Drivetrain drivetrain;
    private DrivetrainStrafe drivetrainStrafe;
    private DrivetrainRotation drivetrainRotation;
    private PIDController pidControllerDistance;
    private Timer timer;
    private double distanceToTravel;
    private double distance;
    
    private final double P = 1;
    private final double I = 0;
    private final double D = 0;
    private final double TIMEOUT_PER_DISTANCE = 5;
    
    public DriveForXDistanceCommand(double distanceToTravel) {
        super("DriveForXDistance");
        this.drivetrain = Components.getInstance().drivetrain;
        this.drivetrainStrafe = Components.getInstance().drivetrainStrafe;
        this.drivetrainRotation = Components.getInstance().drivetrainRotation;
        this.distanceToTravel = distanceToTravel;
        this.pidControllerDistance = new PIDController(P, I, D, drivetrainRotation, drivetrainRotation);
        this.distance = 0;
        setTimeout(distanceToTravel * TIMEOUT_PER_DISTANCE);
        requires(drivetrainStrafe);
        requires(drivetrainRotation);
    }
    
    protected void initialize() {
        pidControllerDistance.enable();
        pidControllerDistance.setSetpoint(0);
        drivetrainStrafe.mecanumPolarStrafe(1, 0);
        timer.start();
    }

    protected void execute() {
        double delta = timer.get();
        distance += (drivetrain.getAverageVelocity() * delta);
        timer.reset();
    }

    protected boolean isFinished() {
        return isTimedOut() || (distance >= distanceToTravel);
    }

    protected void end() {
        pidControllerDistance.disable();
        drivetrainStrafe.mecanumPolarStrafe(0, 0);
        drivetrainRotation.mecanumPolarRotate(0);
        timer.stop();
    }

    protected void interrupted() {
        end();
    }
    
}
