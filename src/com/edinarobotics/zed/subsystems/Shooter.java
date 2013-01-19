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
    
    private double velocity;
    private double position;
    private CANJaguar shooterJaguarFirst;
    private CANJaguar shooterJaguarSecond;
    private CANJaguar anglingJaguar;
    
    public Shooter(int firstShooterJaguarNum, int secondShooterJaguarNum, int anglingJaguarNum) {
        super("Shooter");
        
        try {
            shooterJaguarFirst = new CANJaguar(firstShooterJaguarNum);
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
            shooterJaguarSecond = new CANJaguar(secondShooterJaguarNum);
            shooterJaguarSecond.setSpeedReference(CANJaguar.SpeedReference.kQuadEncoder);
            shooterJaguarSecond.configEncoderCodesPerRev(ENCODER_TICKS_PER_REV_2);
            shooterJaguarSecond.changeControlMode(CANJaguar.ControlMode.kSpeed);
            shooterJaguarSecond.setPID(P_SHOOTER_2, I_SHOOTER_2, D_SHOOTER_2);
        }
        catch(Exception e) {
            System.err.println("Failed to create second shooter Jaguar.");
            e.printStackTrace();
        }
        
        try {
            anglingJaguar = new CANJaguar(anglingJaguarNum);
            anglingJaguar.setPositionReference(CANJaguar.PositionReference.kPotentiometer);
            anglingJaguar.configPotentiometerTurns(ANGLE_POTENTIOMETER_TURNS);
            anglingJaguar.changeControlMode(CANJaguar.ControlMode.kPosition);
            anglingJaguar.setPID(P_ANGLE, I_ANGLE, D_ANGLE);
        }
        catch (Exception e) {
            System.err.println("Failed to create Angling Jaguar.");
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
}
