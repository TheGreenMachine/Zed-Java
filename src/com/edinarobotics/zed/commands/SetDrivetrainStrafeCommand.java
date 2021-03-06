package com.edinarobotics.zed.commands;

import com.edinarobotics.zed.Components;
import com.edinarobotics.zed.subsystems.DrivetrainStrafe;
import edu.wpi.first.wpilibj.command.Command;

public class SetDrivetrainStrafeCommand extends Command{
    private double magnitude, direction;
    private DrivetrainStrafe drivetrainStrafe;
    
    public SetDrivetrainStrafeCommand(double magnitude, double direction){
        super("SetDrivetrainStrafe");
        this.magnitude = magnitude;
        this.direction = direction;
        drivetrainStrafe = Components.getInstance().drivetrainStrafe;
        requires(drivetrainStrafe);
    }

    protected void initialize() {
        drivetrainStrafe.mecanumPolarStrafe(magnitude, direction);
    }

    protected void execute() {
        
    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {
        
    }

    protected void interrupted() {
        
    }
    
    
}
