package com.edinarobotics.zed.subsystems;

import com.edinarobotics.utils.subsystems.Subsystem1816;
import edu.wpi.first.wpilibj.CANJaguar;

public class Shooter extends Subsystem1816 {
    public static final double SHOOTER_ON = 1;
    public static final double SHOOTER_OFF = 0;
    
    private final int NUM_RETRIES = 10;
    
    private double velocity;
    //private CANJaguar shooterJaguarFirst;
    //private CANJaguar shooterJaguarSecond;
    
    public Shooter(int firstShooterJaguarNum, int secondShooterJaguarNum) {
        super("Shooter");
        //shooterJaguarFirst = createCANJaguar(firstShooterJaguarNum, CANJaguar.ControlMode.kPercentVbus, NUM_RETRIES);
        //shooterJaguarSecond = createCANJaguar(secondShooterJaguarNum, CANJaguar.ControlMode.kPercentVbus, NUM_RETRIES);
    }
 
    public void setShooterVelocity(double velocity) {
        this.velocity = velocity;
        update();
    }
    
    public double getFirstJaguarVelocity() {
       /* if(shooterJaguarFirst != null) {
            try {
                return shooterJaguarFirst.getSpeed();
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
        return -1;*/
        return 0;
    }
    
    public double getSecondJaguarVelocity() {
        /*if(shooterJaguarSecond != null) {
            try {
                return shooterJaguarSecond.getSpeed();
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }*/
        return 0;
    }
    
    public void update() {
       /* try {
            if(shooterJaguarFirst != null) {
                shooterJaguarFirst.setX(velocity*0.75);
            }
            if(shooterJaguarSecond != null) {
                shooterJaguarSecond.setX(velocity);
            }
        }
        catch(Exception e) {
            System.err.println("Failed to update shooter Jaguars.");
            e.printStackTrace();
        }*/
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
    
    private CANJaguar createCANJaguar(int id, CANJaguar.ControlMode controlMode, int numRetries){
        CANJaguar canJaguar = null;
        try{
            for(int i = 0; i < numRetries; i++){
                try {
                    canJaguar = new CANJaguar(id);
                    break;
                }
                catch(Exception e){
                    System.out.println("Failed to create CANJaguar");
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
