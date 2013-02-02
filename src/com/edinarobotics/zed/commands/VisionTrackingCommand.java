package com.edinarobotics.zed.commands;

import com.edinarobotics.utils.pid.PIDConfig;
import com.edinarobotics.utils.pid.PIDTuningManager;
import com.edinarobotics.zed.Components;
import com.edinarobotics.zed.subsystems.DrivetrainRotation;
import com.edinarobotics.zed.subsystems.Lifter;
import com.edinarobotics.zed.vision.PIDTargetX;
import com.edinarobotics.zed.vision.PIDTargetY;
import com.edinarobotics.zed.vision.Target;
import com.edinarobotics.zed.vision.TargetCollection;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class VisionTrackingCommand extends Command {
    public static final byte HIGH_GOAL = 1;
    public static final byte MIDDLE_GOAL = 2;
    public static final byte ANY_GOAL = 3;
    
    private NetworkTable visionTable = NetworkTable.getTable("vision");
    private TargetCollection targetCollection;
    private PIDController pidControllerX;
    private PIDController pidControllerY;
    private PIDTargetX pidTargetX;
    private PIDTargetY pidTargetY;
    private DrivetrainRotation drivetrainRotation;
    private Lifter lifter;
    private PIDConfig xPIDConfig;
    private PIDConfig yPIDConfig;
    private byte goalType;
    private double xSetpoint;
    private double ySetpoint;
    
    private final double X_P = 1;
    private final double X_I = 0;
    private final double X_D = 0;
    private final double Y_P = 1;
    private final double Y_I = 1;
    private final double Y_D = 1;
    
    public VisionTrackingCommand() {
        this(ANY_GOAL);
    }
    
    public VisionTrackingCommand(double timeoutSeconds) {
        this();
        setTimeout(timeoutSeconds);
    }
    
    public VisionTrackingCommand(byte goalType, double timeoutSeconds) {
        this(goalType);
        setTimeout(timeoutSeconds);
    }
    
    public VisionTrackingCommand(byte goalType) {
        super("VisionTracking");
        this.goalType = goalType;
        drivetrainRotation = Components.getInstance().drivetrainRotation;
        lifter = Components.getInstance().lifter;
        pidTargetX = new PIDTargetX();
        pidTargetY = new PIDTargetY();
        pidControllerX = new PIDController(X_P, X_I, X_D, pidTargetX, drivetrainRotation);
        pidControllerY = new PIDController(Y_P, Y_I, Y_D, pidTargetY, null);
        xPIDConfig = PIDTuningManager.getInstance().getPIDConfig("Vision Horizontal");
        yPIDConfig = PIDTuningManager.getInstance().getPIDConfig("Vision Vertical");
        xSetpoint = 0;
        ySetpoint = 0;
        requires(drivetrainRotation);
        requires(lifter);
    }
    
    protected void initialize() {
        visionTable.putBoolean("vton", true);
        pidControllerX.enable();
        pidControllerY.enable();
    }

    protected void execute() {
        targetCollection = new TargetCollection(visionTable.getString("vtdata"));
        pidControllerX.setSetpoint(xSetpoint);
        pidControllerY.setSetpoint(ySetpoint);
        Target target;
        if(goalType == HIGH_GOAL) {
            target = targetCollection.getClosestTarget(xSetpoint, ySetpoint, true);
        } else if(goalType == MIDDLE_GOAL) {
            target = targetCollection.getClosestTarget(xSetpoint, ySetpoint, false);
        } else {
            target = targetCollection.getClosestTarget(xSetpoint, ySetpoint);
        }
        pidTargetX.setTarget(target);
        pidTargetY.setTarget(target);
        //PID tuning code
        pidControllerX.setPID(xPIDConfig.getP(X_P), xPIDConfig.getI(X_I), xPIDConfig.getD(X_D));
        xPIDConfig.setSetpoint(pidControllerX.getSetpoint());
        xPIDConfig.setValue(pidTargetX.pidGet());
        pidControllerY.setPID(yPIDConfig.getP(Y_P), yPIDConfig.getI(Y_I), yPIDConfig.getD(Y_D));
        yPIDConfig.setSetpoint(pidControllerY.getSetpoint());
        yPIDConfig.setValue(pidTargetY.pidGet());
    }

    protected boolean isFinished() {
        return (pidControllerX.onTarget() && pidControllerY.onTarget()) ||
                isTimedOut();
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
