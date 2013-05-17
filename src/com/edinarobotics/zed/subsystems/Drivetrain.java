package com.edinarobotics.zed.subsystems;

import com.edinarobotics.utils.controllers.SpeedControllerMultiplexer;
import com.edinarobotics.utils.rate.RampRateHelper;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;

public class Drivetrain {
    private final double DISTANCE_PER_PULSE = 1;
    private RobotDrive robotDrive;
    private double targetMagnitude;
    private double currentMagnitude;
    private double direction;
    private double rotation;
    private static final double JUMP_POINT = 0.5;
    
    private RampRateHelper magnitudeLimit;
    
    private Encoder encoder1;
    private Encoder encoder2;
    
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
        magnitudeLimit = new RampRateHelper(0.8, true, false);
     }
    
    public void mecanumPolarStrafe(double magnitude, double direction){
        this.targetMagnitude = magnitude;
        this.direction = direction;
        update();
    }
    
    public void mecanumPolarRotation(double rotation){
        this.rotation = rotation;
        update();
    }
    
    public void update(){
        magnitudeLimit.setTarget(targetMagnitude);
        if(Math.abs(currentMagnitude) < Math.abs(targetMagnitude) && Math.abs(currentMagnitude) < JUMP_POINT){
            currentMagnitude = signum(targetMagnitude) * JUMP_POINT;
            // TO FIX THIS: currentMagnitude = targetMagnitude;
        }
        currentMagnitude += magnitudeLimit.getChange(currentMagnitude);
        System.out.println("Target: "+magnitudeLimit.getTarget()+"       "+currentMagnitude);
        robotDrive.mecanumDrive_Polar(currentMagnitude, direction, rotation);
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
    
    public void setRateLimitEnabled(boolean enabled){
        magnitudeLimit.setRampDown(false);
        magnitudeLimit.setRampUp(enabled);
    }
    
    private byte signum(double value){
        if (value < 0) {
            return -1;
        } else if (value > 0) {
            return 1;
        }
        return 0;
    }
}
