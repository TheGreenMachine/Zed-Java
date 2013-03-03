package com.edinarobotics.zed.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.RobotDrive;

public class Drivetrain {
    private final double DISTANCE_PER_PULSE = 1;
    private RobotDrive robotDrive;
    private double magnitude;
    private double direction;
    private double rotation;
    
    private Encoder encoder1;
    private Encoder encoder2;
    
    public Drivetrain(int frontLeft, int frontRight, int backLeft, int backRight,
            int encoder1A, int encoder1B, int encoder2A, int encoder2B) {
        this.robotDrive = new RobotDrive(frontLeft, backLeft, frontRight, backRight);
        robotDrive.setInvertedMotor(RobotDrive.MotorType.kFrontRight, true);
        robotDrive.setInvertedMotor(RobotDrive.MotorType.kFrontLeft, false);
        robotDrive.setInvertedMotor(RobotDrive.MotorType.kRearRight, true);
        robotDrive.setInvertedMotor(RobotDrive.MotorType.kRearLeft, false);
        
        encoder1 = new Encoder(encoder1A, encoder1B);
        encoder2 = new Encoder(encoder2A, encoder2B);
        encoder1.setDistancePerPulse(DISTANCE_PER_PULSE);
        encoder2.setDistancePerPulse(DISTANCE_PER_PULSE);
        encoder1.start();
        encoder2.start();
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
    
    public double getAverageForwardVelocity() {
        /*
         * The inner 0.5 multiplier is used because mecanum drive only gives
         * a forward velocity of half the wheel's actual rate.
         */
        return ((0.5*encoder1.getRate()) + (0.5*encoder2.getRate()))/2.0;
    }
}
