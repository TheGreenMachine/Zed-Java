package com.edinarobotics.zed.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.RobotDrive;

public class Drivetrain {
    private final double DISTANCE_PER_PULSE = 1;
    private RobotDrive robotDrive;
    private double magnitude;
    private double direction;
    private double rotation;
    
    private Encoder frontLeftEncoder;
    private Encoder frontRightEncoder;
    private Encoder backLeftEncoder;
    private Encoder backRightEncoder;
    
    public Drivetrain(int frontLeft, int frontRight, int backLeft, int backRight,
            int frontLeftA, int frontLeftB, int frontRightA, int frontRightB,
            int backLeftA, int backLeftB, int backRightA, int backRightB) {
        this.robotDrive = new RobotDrive(frontLeft, backLeft, frontRight, backRight);
        robotDrive.setInvertedMotor(RobotDrive.MotorType.kFrontRight, true);
        robotDrive.setInvertedMotor(RobotDrive.MotorType.kFrontLeft, false);
        robotDrive.setInvertedMotor(RobotDrive.MotorType.kRearRight, true);
        robotDrive.setInvertedMotor(RobotDrive.MotorType.kRearLeft, false);
        
        frontLeftEncoder = new Encoder(frontLeftA, frontLeftB);
        frontRightEncoder = new Encoder(frontRightA, frontRightB);
        backLeftEncoder = new Encoder(backLeftA, backLeftB);
        backRightEncoder = new Encoder(backRightA, backRightB);
        frontLeftEncoder.setDistancePerPulse(DISTANCE_PER_PULSE);
        frontRightEncoder.setDistancePerPulse(DISTANCE_PER_PULSE);
        backLeftEncoder.setDistancePerPulse(DISTANCE_PER_PULSE);
        backRightEncoder.setDistancePerPulse(DISTANCE_PER_PULSE);
        frontLeftEncoder.start();
        frontRightEncoder.start();
        backLeftEncoder.start();
        backRightEncoder.start();
    }
    
    public void mecanumPolarStrafe(double magnitude, double direction){
        this.magnitude = magnitude;
        this.direction = direction;
        update();
    }
    
    public void mecanumPolarRotation(double rotation){
        this.rotation = rotation;
        update();
    }
    
    public void update(){
        robotDrive.mecanumDrive_Polar(magnitude, direction, rotation);
    }
    
    public double getFrontLeftVelocity() {
        return frontLeftEncoder.getRate();
    }
    
    public double getFrontRightVelocity() {
        return frontRightEncoder.getRate();
    }
    
    public double getBackLeftVelocity() {
        return backLeftEncoder.getRate();
    }
    
    public double getBackRightVelocity() {
        return backRightEncoder.getRate();
    }
    
    public double getAverageVelocity() {
        return 0.5 * ((getFrontLeftVelocity() + getFrontRightVelocity() +
                getBackLeftVelocity() + getBackRightVelocity()) / 4);
    }
}
