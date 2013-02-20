package com.edinarobotics.zed.commands;

import com.edinarobotics.utils.pid.PIDConfig;
import com.edinarobotics.utils.pid.PIDTuningManager;
import com.edinarobotics.zed.Components;
import com.edinarobotics.zed.subsystems.DrivetrainRotation;
import com.edinarobotics.zed.subsystems.Lifter;
import com.edinarobotics.zed.vision.LifterTargetY;
import com.edinarobotics.zed.vision.PIDTargetX;
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
    private DrivetrainRotation drivetrainRotation;
    private Lifter lifter;
    private byte goalType;
    
    // X Fields
    private PIDController pidControllerX;
    private PIDTargetX pidTargetX;
    private PIDConfig xPIDConfig;
    private double xSetpoint;
    private double xTolerance;
    
    // Y Fields
    LifterTargetY lifterTargetY;
    private double ySetpoint;
    private double yTolerance;
    
    private final double X_P = -0.99;
    private final double X_I = -0.01;
    private final double X_D = -3.25;
    
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
        pidControllerX = new PIDController(X_P, X_I, X_D, pidTargetX, drivetrainRotation);
        xPIDConfig = PIDTuningManager.getInstance().getPIDConfig("Vision Horizontal");
        xSetpoint = 0;
        xTolerance = 0;
        
        lifterTargetY = new LifterTargetY();
        ySetpoint = 0;
        yTolerance = 0.1;
        
        requires(drivetrainRotation);
        requires(lifter);
    }
    
    protected void initialize() {
        pidControllerX.setSetpoint(0);
        pidControllerX.enable();
        pidControllerX.setAbsoluteTolerance(0);
    }

    protected void execute() {
        targetCollection = new TargetCollection(visionTable.getString("vtdata", ""));
        Target target;
        
        if(goalType == HIGH_GOAL) {
            target = targetCollection.getClosestTarget(xSetpoint, ySetpoint, true);
        } else if(goalType == MIDDLE_GOAL) {
            target = targetCollection.getClosestTarget(xSetpoint, ySetpoint, false);
        } else {
            target = targetCollection.getClosestTarget(xSetpoint, ySetpoint);
        }
        
        if(target != null) {
            xSetpoint = getXSetpoint(target.getDistance());
            ySetpoint = getYSetpoint(target.getDistance());
            pidTargetX.setTarget(target);
            pidControllerX.setSetpoint(xSetpoint);
            pidControllerX.setAbsoluteTolerance(xTolerance);
            lifterTargetY.setTarget(target);
            lifterTargetY.setYSetpoint(ySetpoint);
            lifterTargetY.setYTolerance(yTolerance);
            lifter.setLifterDirection(lifterTargetY.targetY());
        } else {
            drivetrainRotation.update();
            lifter.update();
        }
        
        //PID tuning code
        pidControllerX.setPID(xPIDConfig.getP(X_P), xPIDConfig.getI(X_I), xPIDConfig.getD(X_D));
        xPIDConfig.setSetpoint(pidControllerX.getSetpoint());
        xPIDConfig.setValue(pidTargetX.pidGet());
    }

    protected boolean isFinished() {
        return (pidControllerX.onTarget() && lifterTargetY.onTarget()) ||
                isTimedOut();
    }

    protected void end() {
        pidControllerX.disable();
        pidControllerX.reset();
        lifter.setLifterDirection(Lifter.LifterDirection.LIFTER_STOP);
        drivetrainRotation.mecanumPolarRotate(0);
        System.out.println("VISION TRACKING DONE");
    }

    protected void interrupted() {
        end();
    }
    
    private double getXSetpoint(double distance){
        return 0.0278026829*distance - 0.6818562776;
    }
    
    private double getYSetpoint(double distance){
        return -0.023267714*distance + 0.4098144504;
    }
}
