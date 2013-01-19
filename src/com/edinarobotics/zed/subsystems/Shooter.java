package com.edinarobotics.zed.subsystems;

import com.edinarobotics.utils.commands.MaintainStateCommand;
import com.edinarobotics.utils.subsystems.Subsystem1816;
import edu.wpi.first.wpilibj.CANJaguar;

public class Shooter extends Subsystem1816 {
    private static final int ENCODER_TICKS_PER_REV_1 = 180;
    private static final int ENCODER_TICKS_PER_REV_2 = 180;
    private final double P_SHOOTER_1 = 1;
    private final double I_SHOOTER_1 = 0;
    private final double D_SHOOTER_1 = 0;
    private final double P_SHOOTER_2 = P_SHOOTER_1;
    private final double I_SHOOTER_2 = I_SHOOTER_1;
    private final double D_SHOOTER_2 = D_SHOOTER_1;
    
    private double velocity;
    private CANJaguar shooterJaguarFirst;
    private CANJaguar shooterJaguarSecond;
    
    public Shooter(int shooterJaguarPortNumber) {
        try {
            shooterJaguarFirst = new CANJaguar(shooterJaguarPortNumber);
            shooterJaguarFirst.setSpeedReference(CANJaguar.SpeedReference.kQuadEncoder);
            shooterJaguarFirst.configEncoderCodesPerRev(ENCODER_TICKS_PER_REV_1);
            shooterJaguarFirst.changeControlMode(CANJaguar.ControlMode.kSpeed);
            shooterJaguarFirst.setPID(P_SHOOTER_1, I_SHOOTER_1, D_SHOOTER_1);
        }
        catch(Exception e) {
            System.err.println("Failed to create first shooter Jaguar.");
            e.printStackTrace();
        }
        try {
            shooterJaguarSecond = new CANJaguar(shooterJaguarPortNumber);
            shooterJaguarSecond.setSpeedReference(CANJaguar.SpeedReference.kQuadEncoder);
            shooterJaguarSecond.configEncoderCodesPerRev(ENCODER_TICKS_PER_REV_2);
            shooterJaguarSecond.changeControlMode(CANJaguar.ControlMode.kSpeed);
            shooterJaguarSecond.setPID(P_SHOOTER_2, I_SHOOTER_2, D_SHOOTER_2);
        }
        catch(Exception e) {
            System.err.println("Failed to create second shooter Jaguar.");
            e.printStackTrace();
        }
    }
    
    protected void initDefaultCommand() {
        setDefaultCommand(new MaintainStateCommand(this));
    }
    
    public void setShooterVelocity(double velocity) {
        this.velocity = velocity;
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
        }
        catch(Exception e) {
            System.err.println("Failed to update shooter Jaguar.");
            e.printStackTrace();
        }
    }
}
