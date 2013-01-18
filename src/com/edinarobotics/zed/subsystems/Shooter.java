package com.edinarobotics.zed.subsystems;

import com.edinarobotics.utils.commands.MaintainStateCommand;
import com.edinarobotics.utils.subsystems.Subsystem1816;
import edu.wpi.first.wpilibj.CANJaguar;

public class Shooter extends Subsystem1816 {
    private static final int ENCODER_TICKS_PER_REV = 180;
    private final double P = 1;
    private final double I = 0;
    private final double D = 0;
    
    private double velocity;
    private CANJaguar shooterJaguar;
    
    public Shooter(int shooterJaguarPortNumber) {
        try {
            shooterJaguar = new CANJaguar(shooterJaguarPortNumber);
            shooterJaguar.configEncoderCodesPerRev(ENCODER_TICKS_PER_REV);
            shooterJaguar.changeControlMode(CANJaguar.ControlMode.kSpeed);
            shooterJaguar.setPID(P, I, D);
        }
        catch(Exception e) {
            System.err.println("Failed to create shooter Jaguar.");
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
            if(shooterJaguar != null) {
                shooterJaguar.setX(velocity);
            }
        }
        catch(Exception e) {
            System.err.println("Failed to update shooter Jaguar.");
            e.printStackTrace();
        }
    }
}
