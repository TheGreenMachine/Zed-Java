package com.edinarobotics.zed.subsystems;

import com.edinarobotics.utils.pid.PIDConfig;
import com.edinarobotics.utils.pid.PIDTuningManager;
import com.edinarobotics.utils.subsystems.Subsystem1816;
import edu.wpi.first.wpilibj.CANJaguar;

public class Shooter extends Subsystem1816 {
    public static final double SHOOTER_ON = 3000;
    public static final double SHOOTER_OFF = 0;
    
    private static final int ENCODER_TICKS_PER_REV_1 = 180;
    private static final int ENCODER_TICKS_PER_REV_2 = 180;
    private final double P_SHOOTER_1 = 1;
    private final double I_SHOOTER_1 = 0;
    private final double D_SHOOTER_1 = 0;
    private final double P_SHOOTER_2 = 1;
    private final double I_SHOOTER_2 = 0;
    private final double D_SHOOTER_2 = 0;
    private final int NUM_RETRIES = 10;
    private PIDConfig firstShooterPIDConfig;
    private PIDConfig secondShooterPIDConfig;
    
    private double velocity;
    private CANJaguar shooterJaguarFirst;
    private CANJaguar shooterJaguarSecond;
    
    public Shooter(int firstShooterJaguarNum, int secondShooterJaguarNum) {
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
        firstShooterPIDConfig = PIDTuningManager.getInstance().getPIDConfig("First Shooter");
        secondShooterPIDConfig = PIDTuningManager.getInstance().getPIDConfig("Second Shooter");
    }
    
    public void setShooterVelocity(double velocity) {
        this.velocity = velocity;
        update();
    }
    
    public void update() {
        try {
            if(shooterJaguarFirst != null) {
                shooterJaguarFirst.setX(velocity);
                //PID tuning code
                shooterJaguarFirst.setPID(firstShooterPIDConfig.getP(P_SHOOTER_1),
                        firstShooterPIDConfig.getI(I_SHOOTER_1),
                        firstShooterPIDConfig.getD(D_SHOOTER_1));
                firstShooterPIDConfig.setSetpoint(velocity);
                firstShooterPIDConfig.setValue(shooterJaguarFirst.getSpeed());
            }
            if(shooterJaguarSecond != null) {
                shooterJaguarSecond.setX(velocity);
                //PID tuning code
                shooterJaguarSecond.setPID(secondShooterPIDConfig.getP(P_SHOOTER_2),
                        secondShooterPIDConfig.getI(I_SHOOTER_2),
                        secondShooterPIDConfig.getD(D_SHOOTER_2));
                secondShooterPIDConfig.setSetpoint(velocity);
                secondShooterPIDConfig.setValue(shooterJaguarSecond.getSpeed());
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

}
