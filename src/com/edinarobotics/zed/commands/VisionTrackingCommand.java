package com.edinarobotics.zed.commands;

import com.edinarobotics.zed.Components;
import com.edinarobotics.zed.subsystems.DrivetrainRotation;
import com.edinarobotics.zed.vision.PIDTargetX;
import com.edinarobotics.zed.vision.PIDTargetY;
import com.edinarobotics.zed.vision.TargetCollection;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class VisionTrackingCommand extends Command {
    private NetworkTable visionTable = NetworkTable.getTable("vision");
    private TargetCollection targetCollection;
    public PIDController pidControllerX;
    public PIDController pidControllerY;
    public PIDTargetX pidTargetX;
    public PIDTargetY pidTargetY;
    public DrivetrainRotation drivetrainRotation;
    
    private final double X_P = 1;
    private final double X_I = 0;
    private final double X_D = 0;
    private final double Y_P = 1;
    private final double Y_I = 1;
    private final double Y_D = 1;
    
    public VisionTrackingCommand() {
        drivetrainRotation = Components.getInstance().drivetrainRotation;
        pidTargetX = new PIDTargetX();
        pidTargetY = new PIDTargetY();
        pidControllerX = new PIDController(X_P, X_I, X_D, pidTargetX, drivetrainRotation);
        pidControllerY = new PIDController(Y_P, Y_I, Y_D, pidTargetY, drivetrainRotation);
    }
    
    protected void initialize() {
        visionTable.putBoolean("vton", true);
        pidControllerX.enable();
        pidControllerY.enable();
    }

    protected void execute() {
        targetCollection = new TargetCollection(visionTable.getString("vtdata"));
        pidControllerX.setSetpoint(0);
        pidControllerY.setSetpoint(0);
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        visionTable.putBoolean("vton", false);
        pidControllerX.disable();
        pidControllerY.disable();
    }

    protected void interrupted() {
        end();
    }
    
}
