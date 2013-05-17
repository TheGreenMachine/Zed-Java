package com.edinarobotics.zed.subsystems;

import com.edinarobotics.utils.subsystems.Subsystem1816;
import com.sun.squawk.util.MathUtils;
import edu.wpi.first.wpilibj.ADXL345_I2C;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStationLCD;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.Victor;

public class Lifter extends Subsystem1816 implements PIDSource, PIDOutput {
    private static final double MIN_VOLTAGE = 0;
    private static final double MAX_VOLTAGE = 5;
    private static final double STRING_LENGTH = 1;
    private static final double STRING_POT_POSITION = 1;
    
    public static final double LIFTER_UP = -1;
    public static final double LIFTER_DOWN = 1;
    public static final double LIFTER_STOP = 0;
    
    public static final double PYRAMID_BACK_MIDDLE_ANGLE = 33;
    
    private double velocity;
    private Victor lifterVictor;
    private ADXL345_I2C accelerometer;
    private DigitalInput upperSwitch;
    private DigitalInput lowerSwitch;
    private DriverStationLCD textOutput = DriverStationLCD.getInstance();
    
    /**
     * 
     * @param lifterRelay
     * @param lifterStringPot
     * @param upperLimitSwitch
     * @param lowerLimitSwitch 
     */
    public Lifter(int lifterVictor, int lifterStringPot, int upperLimitSwitch, int lowerLimitSwitch) {
        super("Lifter");
        this.lifterVictor = new Victor(lifterVictor);
        accelerometer = new ADXL345_I2C(1, ADXL345_I2C.DataFormat_Range.k2G);
        upperSwitch = new DigitalInput(upperLimitSwitch);
        lowerSwitch = new DigitalInput(lowerLimitSwitch);
    }
    
    public void setLifterDirection(double velocity){
        this.velocity = velocity;
        update();
    }
    
    public double getLifterVelocity(){
        return velocity;
    }
    
    public double getStringPotLength() {
        return STRING_LENGTH;
    }
    
    public double getShooterAngle() {
        return Math.toDegrees(MathUtils.acos(accelerometer.getAcceleration(ADXL345_I2C.Axes.kX)));
    }
    
    public double pidGet() {
        return getShooterAngle();
    }
    
    public void update() {
        if((signum(velocity) == signum(LIFTER_UP) && getUpperLimitSwitch()) ||
           (signum(velocity) == signum(LIFTER_DOWN) && getLowerLimitSwitch())) {
            lifterVictor.set(LIFTER_STOP);
        }
        else{
            lifterVictor.set(velocity);
        }
    }
    
    public boolean getUpperLimitSwitch(){
        return upperSwitch.get();
    }
    
    public boolean getLowerLimitSwitch(){
        return lowerSwitch.get();
    }
    
    private byte signum(double value){
        if(value > 0){
            return 1;
        }
        else if(value < 0){
            return -1;
        }
        return 0;
    }

    public void pidWrite(double output) {
        setLifterDirection(output);
    }
}
