package com.edinarobotics.zed.subsystems;

import com.edinarobotics.utils.controllers.SpeedControllerMultiplexer;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;

public class Drivetrain {
    private final double DISTANCE_PER_PULSE = 1;
    private RobotDrive robotDrive;
    private double magnitude;
    private double direction;
    private double rotation;
    
    private Encoder encoder1;
    private Encoder encoder2;
    
    /*
    public Drivetrain(int frontLeft, int frontRight, int backLeft, int backRight) {
        this.robotDrive = new RobotDrive(frontLeft, backLeft, frontRight, backRight);
        robotDrive.setInvertedMotor(RobotDrive.MotorType.kFrontRight, true);
        robotDrive.setInvertedMotor(RobotDrive.MotorType.kFrontLeft, false);
        robotDrive.setInvertedMotor(RobotDrive.MotorType.kRearRight, true);
        robotDrive.setInvertedMotor(RobotDrive.MotorType.kRearLeft, false);
    }
    */
    
    public Drivetrain(int frontLeft, int frontLeftMini, int frontRight, int frontRightMini,
        int backLeft, int backLeftMini, int backRight, int backRightMini) {
        this.robotDrive = new RobotDrive(createMultiplexedJaguar(frontLeft, frontLeftMini),
            createMultiplexedJaguar(backLeft, backLeftMini),
            createMultiplexedJaguar(frontRight, frontRightMini),
            createMultiplexedJaguar(backRight, backRightMini));
        robotDrive.setInvertedMotor(RobotDrive.MotorType.kFrontRight, true);
        robotDrive.setInvertedMotor(RobotDrive.MotorType.kFrontLeft, false);
        robotDrive.setInvertedMotor(RobotDrive.MotorType.kRearRight, true);
        robotDrive.setInvertedMotor(RobotDrive.MotorType.kRearLeft, false);
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
    
    /**
     * @deprecated The encoder does not get constructed. Usage will cause exceptions!
     */
    public double getAverageForwardVelocity() {
        /*
         * The inner 0.5 multiplier is used because mecanum drive only gives
         * a forward velocity of half the wheel's actual rate.
         */
        return ((0.5*encoder1.getRate()) + (0.5*encoder2.getRate()))/2.0;
    }
    
    private SpeedController createMultiplexedJaguar(int id, int idMini){
        Jaguar[] jaguars = new Jaguar[2];
        jaguars[0] = new Jaguar(id);
        jaguars[1] = new Jaguar(idMini);
        
        SpeedControllerMultiplexer scMultiplexer = new SpeedControllerMultiplexer(jaguars);
        return scMultiplexer;
    }
}
