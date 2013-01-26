package com.edinarobotics.zed.subsystems;

import com.edinarobotics.utils.commands.MaintainStateCommand;
import com.edinarobotics.utils.subsystems.Subsystem1816;
import edu.wpi.first.wpilibj.CANJaguar;

public class Shooter extends Subsystem1816 {
    private static final int ENCODER_TICKS_PER_REV_1 = 180;
    private static final int ENCODER_TICKS_PER_REV_2 = 180;
    private static final int ANGLE_POTENTIOMETER_TURNS = 1;
    private final double P_SHOOTER_1 = 1;
    private final double I_SHOOTER_1 = 0;
    private final double D_SHOOTER_1 = 0;
    private final double P_SHOOTER_2 = P_SHOOTER_1;
    private final double I_SHOOTER_2 = I_SHOOTER_1;
    private final double D_SHOOTER_2 = D_SHOOTER_1;
    private final double P_ANGLE = 1;
    private final double I_ANGLE = 0;
    private final double D_ANGLE = 0;
    private final int NUM_RETRIES = 10;
    
    private double velocity;
    private double position;
    private CANJaguar shooterJaguarFirst;
    private CANJaguar shooterJaguarSecond;
    private CANJaguar anglingJaguar;
    
    public Shooter(int firstShooterJaguarNum, int secondShooterJaguarNum, int anglingJaguarNum) {
        super("Shooter");
        shooterJaguarFirst = createCANJaguar(firstShooterJaguarNum,
                CANJaguar.SpeedReference.kQuadEncoder,
                ENCODER_TICKS_PER_REV_1,
                CANJaguar.ControlMode.kSpeed,
                P_SHOOTER_1, I_SHOOTER_1, D_SHOOTER_1,
                NUM_RETRIES);
        shooterJaguarSecond = createCANJaguar(secondShooterJaguarNum,
                CANJaguar.SpeedReference.kQuadEncoder,
                ENCODER_TICKS_PER_REV_2,
                CANJaguar.ControlMode.kSpeed,
                P_SHOOTER_2, I_SHOOTER_2, D_SHOOTER_2,
                NUM_RETRIES);
        anglingJaguar = createCANJaguar(anglingJaguarNum,
                CANJaguar.PositionReference.kPotentiometer,
                ANGLE_POTENTIOMETER_TURNS,
                CANJaguar.ControlMode.kPosition,
                P_ANGLE, I_ANGLE, D_ANGLE,
                NUM_RETRIES);
    }
    
    protected void initDefaultCommand() {
        setDefaultCommand(new MaintainStateCommand(this));
    }
    
    public void setShooterVelocity(double velocity) {
        this.velocity = velocity;
        update();
    }
    
    public void setShooterPosition(double position) {
        this.position = position;
        update();
    }
    
    public void update() {
        try {
            if(shooterJaguarFirst != null) {
                shooterJaguarFirst.setX(velocity);
            }
            if(shooterJaguarSecond != null) {
                shooterJaguarSecond.setX(velocity);
            }
            if(anglingJaguar != null) {
                anglingJaguar.setX(position);
            }
        }
        catch(Exception e) {
            System.err.println("Failed to update shooter Jaguars.");
            e.printStackTrace();
        }
    }
    
    private CANJaguar createCANJaguar(int id, CANJaguar.SpeedReference speedReference,
            int codesPerRev, CANJaguar.ControlMode controlMode,
            double P, double I, double D, int numRetries) {
        CANJaguar canJaguar = null;
        try {
            for(int i = 0; i < numRetries; i++) {
                canJaguar = new CANJaguar(id);
                if(canJaguar != null) {
                    break;
                }
            }
            canJaguar.setSpeedReference(speedReference);
            canJaguar.configEncoderCodesPerRev(codesPerRev);
            canJaguar.changeControlMode(controlMode);
            canJaguar.setPID(P, I, D);
        }
        catch(Exception e) {
            System.err.println("Failed to create Jaguar.");
            e.printStackTrace();
        }
        return canJaguar;
    }
    
    private CANJaguar createCANJaguar(int id, CANJaguar.PositionReference positionReference,
            int codesPerRev, CANJaguar.ControlMode controlMode,
            double P, double I, double D, int numRetries) {
        CANJaguar canJaguar = null;
        try {
            for(int i = 0; i < numRetries; i++) {
                canJaguar = new CANJaguar(id);
                if(canJaguar != null) {
                    break;
                }
            }
            canJaguar.setPositionReference(positionReference);
            canJaguar.configEncoderCodesPerRev(codesPerRev);
            canJaguar.changeControlMode(controlMode);
            canJaguar.setPID(P, I, D);
        }
        catch(Exception e) {
            System.err.println("Failed to create Jaguar.");
            e.printStackTrace();
        }
        return canJaguar;
    }
}
