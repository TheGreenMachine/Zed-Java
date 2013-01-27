package com.edinarobotics.zed.commands;

import com.edinarobotics.utils.pid.PIDConfig;
import com.edinarobotics.utils.pid.PIDTuningManager;
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
    private PIDController pidControllerX;
    private PIDController pidControllerY;
    private PIDTargetX pidTargetX;
    private PIDTargetY pidTargetY;
    private DrivetrainRotation drivetrainRotation;
    private PIDConfig xPIDConfig;
    private PIDConfig yPIDConfig;
    
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
        xPIDConfig = PIDTuningManager.getInstance().getPIDConfig("Vision Horizontal");
        yPIDConfig = PIDTuningManager.getInstance().getPIDConfig("Vision Vertical");
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
        //PID tuning code
        pidControllerX.setPID(xPIDConfig.getP(X_P), xPIDConfig.getI(X_I), xPIDConfig.getD(X_D));
        xPIDConfig.setSetpoint(pidControllerX.getSetpoint());
        xPIDConfig.setValue(pidTargetX.pidGet());
        pidControllerY.setPID(yPIDConfig.getP(Y_P), yPIDConfig.getI(Y_I), yPIDConfig.getD(Y_D));
        yPIDConfig.setSetpoint(pidControllerY.getSetpoint());
        yPIDConfig.setValue(pidTargetY.pidGet());
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
