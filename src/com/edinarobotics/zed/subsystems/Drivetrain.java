package com.edinarobotics.zed.subsystems;

import com.edinarobotics.utils.controllers.SpeedControllerMultiplexer;
import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;

public class Drivetrain {
    private final double DISTANCE_PER_PULSE = 1;
    private final int NUM_RETRIES = 10;
    private RobotDrive robotDrive;
    private double magnitude;
    private double direction;
    private double rotation;
    
    private Encoder encoder1;
    private Encoder encoder2;
    
    public Drivetrain(int frontLeft, int frontRight, int backLeft, int backRight) {
        this.robotDrive = new RobotDrive(createCANJaguar(frontLeft, NUM_RETRIES),
                createCANJaguar(backLeft, NUM_RETRIES),
                createCANJaguar(frontRight, NUM_RETRIES),
                createCANJaguar(backRight, NUM_RETRIES));
        robotDrive.setInvertedMotor(RobotDrive.MotorType.kFrontRight, true);
        robotDrive.setInvertedMotor(RobotDrive.MotorType.kFrontLeft, false);
        robotDrive.setInvertedMotor(RobotDrive.MotorType.kRearRight, true);
        robotDrive.setInvertedMotor(RobotDrive.MotorType.kRearLeft, false);
    }
    
    public Drivetrain(int frontLeft, int frontLeftMini, int frontRight, int frontRightMini,
            int backLeft, int backLeftMini, int backRight, int backRightMini) {
        this.robotDrive = new RobotDrive(createMultiplexedCANJaguar(frontLeft, frontLeftMini, NUM_RETRIES),
                createMultiplexedCANJaguar(backLeft, backLeftMini, NUM_RETRIES),
                createMultiplexedCANJaguar(frontRight, frontRightMini, NUM_RETRIES),
                createMultiplexedCANJaguar(backRight, backRightMini, NUM_RETRIES));
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
    
    private SpeedController createMultiplexedCANJaguar(int id, int idMini, int numRetries){
        CANJaguar[] canJaguar = new CANJaguar[2];
        canJaguar[0] = createCANJaguar(id, numRetries);
        canJaguar[1] = createCANJaguar(idMini, numRetries);
        
        SpeedControllerMultiplexer scMultiplexer = new SpeedControllerMultiplexer(canJaguar);
        
        return scMultiplexer;
    }
    
    private CANJaguar createCANJaguar(int id, int numRetries){
        return createCANJaguar(id, CANJaguar.ControlMode.kPercentVbus, numRetries);
    }
    
    private CANJaguar createCANJaguar(int id, CANJaguar.ControlMode controlMode, int numRetries){
        CANJaguar canJaguar = null;
        try{
            for(int i = 0; i < numRetries; i++){
                try {
                    canJaguar = new CANJaguar(id);
                    break;
                }
                catch(Exception e){
                    System.out.println("Failed to create CANJaguar("+id+")");
                    e.printStackTrace();
                }
            }
            canJaguar.changeControlMode(controlMode);
        }
        catch(Exception e){
            System.err.println("Failed to create CANJaguar");
            e.printStackTrace();
        }
        return canJaguar;
    }
}
