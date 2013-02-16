package com.edinarobotics.zed.subsystems;

import com.edinarobotics.utils.sensors.StringPot;
import com.edinarobotics.utils.subsystems.Subsystem1816;
import com.sun.squawk.util.MathUtils;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.Relay;

public class Lifter extends Subsystem1816 implements PIDSource {
    private static final double MIN_VOLTAGE = 0;
    private static final double MAX_VOLTAGE = 5;
    private static final double STRING_LENGTH = 1;
    private static final double STRING_POT_POSITION = 1;
    
    private LifterDirection direction;
    private Relay lifterRelay;
    private StringPot stringPot;
    private DigitalInput lowerSwitch;
    
    public Lifter(int lifterRelay, int lifterStringPot, int lowerLimitSwitch) {
        super("Lifter");
        this.lifterRelay = new Relay(lifterRelay);
        stringPot = new StringPot(lifterStringPot, MIN_VOLTAGE, MAX_VOLTAGE, STRING_LENGTH);
        direction = LifterDirection.LIFTER_STOP;
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
        return Math.toDegrees(MathUtils.atan(stringPot.getStringLength() / STRING_POT_POSITION));
    }
    
    public double pidGet() {
        return getShooterAngle();
    }
    
    public void update() {
        LifterDirection processedDirection = direction;
        if(direction.equals(LifterDirection.LIFTER_DOWN) && getLowerLimitSwitch()){
            processedDirection = LifterDirection.LIFTER_STOP;
        }
        lifterRelay.set(processedDirection.getRelayValue());
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
