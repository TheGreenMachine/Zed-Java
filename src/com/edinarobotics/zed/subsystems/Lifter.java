package com.edinarobotics.zed.subsystems;

import com.edinarobotics.utils.sensors.StringPot;
import com.edinarobotics.utils.subsystems.Subsystem1816;
import com.sun.squawk.util.MathUtils;
import edu.wpi.first.wpilibj.ADXL345_I2C;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStationLCD;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.Relay;

public class Lifter extends Subsystem1816 implements PIDSource {
    private static final double MIN_VOLTAGE = 0;
    private static final double MAX_VOLTAGE = 5;
    private static final double STRING_LENGTH = 1;
    private static final double STRING_POT_POSITION = 1;
    
    public static final double PYRAMID_BACK_MIDDLE_ANGLE = 33;
    
    private LifterDirection direction;
    private Relay lifterRelay;
    private ADXL345_I2C accelerometer;
    private DigitalInput upperSwitch;
    private DigitalInput lowerSwitch;
    private DriverStationLCD textOutput = DriverStationLCD.getInstance();
    
    public Lifter(int lifterRelay, int lifterStringPot, int upperLimitSwitch, int lowerLimitSwitch) {
        super("Lifter");
        this.lifterRelay = new Relay(lifterRelay);
        accelerometer = new ADXL345_I2C(1, ADXL345_I2C.DataFormat_Range.k2G);
        direction = LifterDirection.LIFTER_STOP;
        upperSwitch = new DigitalInput(upperLimitSwitch);
        lowerSwitch = new DigitalInput(lowerLimitSwitch);
    }
    
    public void setLifterDirection(LifterDirection direction){
        this.direction = direction;
        update();
    }
    
    public LifterDirection getLifterDirection(){
        return direction;
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
        LifterDirection processedDirection = direction;
        if((direction.equals(LifterDirection.LIFTER_UP) && getUpperLimitSwitch()) ||
           (direction.equals(LifterDirection.LIFTER_DOWN) && getLowerLimitSwitch())){
            processedDirection = LifterDirection.LIFTER_STOP;
        }
        lifterRelay.set(processedDirection.getRelayValue());
        textOutput.println(DriverStationLCD.Line.kUser4, 1, "Shoot Ang: "+getShooterAngle());
        textOutput.updateLCD();
    }
    
    public boolean getUpperLimitSwitch(){
        return upperSwitch.get();
    }
    
    public boolean getLowerLimitSwitch(){
        return lowerSwitch.get();
    }
    
    public static class LifterDirection {
        public static final LifterDirection LIFTER_UP = new LifterDirection((byte)1);
        public static final LifterDirection LIFTER_DOWN = new LifterDirection((byte)-1);
        public static final LifterDirection LIFTER_STOP = new LifterDirection((byte)0);
        
        private byte value;
        
        private LifterDirection(byte value){
            this.value = value;
        }
        
        protected byte getValue(){
            return value;
        }
        
        public boolean equals(Object other){
            if(other instanceof LifterDirection){
                return ((LifterDirection)other).getValue() == getValue();
            }
            return false;
        }
        
        public int hashCode(){
            return new Byte(getValue()).hashCode();
        }
        
        public Relay.Value getRelayValue(){
            if(equals(LIFTER_UP)){
                return Relay.Value.kReverse;
            }
            else if(equals(LIFTER_DOWN)){
                return Relay.Value.kForward;
            }
            return Relay.Value.kOff;
        }
    }
}
