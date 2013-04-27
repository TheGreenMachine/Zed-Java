package com.edinarobotics.zed.vision;

import com.edinarobotics.utils.pid.PIDConfig;
import com.edinarobotics.utils.pid.PIDTuningManager;
import com.edinarobotics.zed.Components;
import com.edinarobotics.zed.subsystems.DrivetrainRotation;
import com.edinarobotics.zed.subsystems.Lifter;
import edu.wpi.first.wpilibj.DriverStationLCD;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

/**
 *
 */
public abstract class VisionTrackingCommand extends Command {
    private static final double xP = -0.99;
    private static final double xI = -0.01;
    private static final double xD = -3.25;
    private static final double xF = 0;
    
    private DriverStationLCD textOutput;
    private NetworkTable visionTable;
    private PIDController xPIDController;
    private PIDConfig xPIDConfig;
    private TargetXPIDSource xPIDSource;
    private Lifter lifter;
    private DrivetrainRotation drivetrainRotation;
    
    public VisionTrackingCommand(){
        super("VisionTracking");
        lifter = Components.getInstance().lifter;
        drivetrainRotation = Components.getInstance().drivetrainRotation;
        requires(lifter);
        requires(drivetrainRotation);
        visionTable = NetworkTable.getTable("vision");
        xPIDSource = new TargetXPIDSource();
        xPIDController = new PIDController(xP, xI, xD, xF, xPIDSource, drivetrainRotation);
        xPIDConfig = PIDTuningManager.getInstance().getPIDConfig("Vision Horizontal");
        textOutput = DriverStationLCD.getInstance();
    }
    
    protected void initialize(){
        xPIDController.setSetpoint(getXSetpoint());
        xPIDController.setAbsoluteTolerance(getXTolerance());
        xPIDController.reset();
        xPIDController.enable();
    }
    
    protected void execute(){
        Target workingTarget = getTarget();
        
        if(workingTarget != null){
            xPIDSource.setTarget(workingTarget);
            xPIDController.setSetpoint(getXSetpoint());
            xPIDController.setAbsoluteTolerance(getXTolerance());
            xPIDController.setPID(xPIDConfig.getP(xP), xPIDConfig.getI(xI), xPIDConfig.getD(xD), xPIDConfig.getF(xF));
            xPIDConfig.setSetpoint(xPIDController.getSetpoint()); //Tuning feedback
            xPIDConfig.setValue(xPIDSource.pidGet()); //Tuning feedback
            
            double yError = getYError(workingTarget);
            if(!isYOnTarget(workingTarget)){
                //Error is too large, correct
                if(yError > 0){
                    //Target is too high, need to move up
                    lifter.setLifterDirection(Lifter.LifterDirection.LIFTER_UP);
                    reportMotion(true);
                }
                else if(yError < 0){
                    //Target is too low, neeed to move down
                    lifter.setLifterDirection(Lifter.LifterDirection.LIFTER_DOWN);
                    reportMotion(false);
                }
                else{
                    //On target
                    lifter.setLifterDirection(Lifter.LifterDirection.LIFTER_STOP);
                    reportStatus("WORKING");
                }
            }
        }
        else{
            reportStatus("NO TARGET");
            drivetrainRotation.mecanumPolarRotate(0);
            xPIDController.setPID(0, 0, 0, 0);
            lifter.setLifterDirection(Lifter.LifterDirection.LIFTER_STOP);
        }
    }
    
    private double getYError(Target target){
        return target.getY() - getYSetpoint(); //If positive, target is too high
    }
    
    private boolean isYOnTarget(Target target){
        return Math.abs(getYError(target)) <= getYTolerance();
    }
    
    private void reportMotion(boolean up){
        if(up && lifter.getUpperLimitSwitch()){
            reportStatus("BACK UP");
        }
        else if(!up && lifter.getLowerLimitSwitch()){
            reportStatus("GO FORWARD");
        }
        else{
            reportStatus("WORKING");
        }
    }
    
    private void reportStatus(String message){
        textOutput.println(DriverStationLCD.Line.kUser1, 1, "VT: "+message+"                                                                ");
        textOutput.updateLCD();
    }
    
    protected void end(){
        xPIDController.disable();
        xPIDController.reset();
        lifter.setLifterDirection(Lifter.LifterDirection.LIFTER_STOP);
        drivetrainRotation.mecanumPolarRotate(0);
        textOutput.println(DriverStationLCD.Line.kUser1, 1, "                                                                ");
        textOutput.updateLCD();
    }
    
    protected void interrupted(){
        end();
    }
    
    protected boolean isFinished(){
        return xPIDController.onTarget() && isYOnTarget(getTarget());
    }
    
    protected abstract double getXSetpoint();
    
    protected abstract double getYSetpoint();
    
    protected double getXTolerance(){
        return 0;
    }
    
    protected double getYTolerance(){
        return 0.08;
    }
    
    protected TargetCollection getTargetCollection(){
        return new TargetCollection(visionTable.getString("vtdata", ""));
    }
    
    protected Target getTarget(){
        return getTargetCollection().getClosestTarget(getXSetpoint(), getYSetpoint());
    }
}
