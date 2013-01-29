package com.edinarobotics.zed.commands;

import com.edinarobotics.zed.Components;
import com.edinarobotics.zed.subsystems.DrivetrainRotation;
import edu.wpi.first.wpilibj.command.Command;

public class SetDrivetrainRotationCommand extends Command{
    private double rotation;
    private DrivetrainRotation drivetrainRotation;
    
    public SetDrivetrainRotationCommand(double rotation){
        super("SetDrivetrainRotation");
        this.rotation = rotation;
        drivetrainRotation = Components.getInstance().drivetrainRotation;
        requires(drivetrainRotation);
    }

    protected void initialize() {
        drivetrainRotation.mecanumPolarRotate(rotation);
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
