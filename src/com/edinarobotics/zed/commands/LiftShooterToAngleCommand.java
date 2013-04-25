package com.edinarobotics.zed.commands;

import com.edinarobotics.zed.Components;
import com.edinarobotics.zed.subsystems.Lifter;
import edu.wpi.first.wpilibj.command.Command;

public class LiftShooterToAngleCommand extends Command {
    private Lifter lifter;
    private double targetAngle;
    
    private final double TOLERANCE;
    
    public LiftShooterToAngleCommand(double angle, double tolerance) {
        super("LiftShooterToAngle");
        this.lifter = Components.getInstance().lifter;
        this.targetAngle = angle;
        TOLERANCE = tolerance;
        setTimeout(8);
    }
    
    public LiftShooterToAngleCommand(double angle){
        this(angle, 2);
    }
    
    protected void initialize() {
    }

    protected void execute() {
        double lifterAngle = lifter.getShooterAngle();
        double liftError = lifterAngle - targetAngle;
        if(liftError > 0){
            lifter.setLifterDirection(Lifter.LifterDirection.LIFTER_DOWN);
        }
        else if(liftError < 0){
            lifter.setLifterDirection(Lifter.LifterDirection.LIFTER_UP);
        }
        else{
            lifter.setLifterDirection(Lifter.LifterDirection.LIFTER_STOP);
        }
    }

    protected boolean isFinished() {
        double lifterAngle = lifter.getShooterAngle();
        double liftError = lifterAngle - targetAngle;
        return Math.abs(liftError) <= TOLERANCE;
    }

    protected void end() {
        lifter.setLifterDirection(Lifter.LifterDirection.LIFTER_STOP);
    }

    protected void interrupted() {
        end();
    }
}
