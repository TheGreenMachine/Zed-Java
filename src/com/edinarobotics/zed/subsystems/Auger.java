package com.edinarobotics.zed.subsystems;

import com.edinarobotics.utils.subsystems.Subsystem1816;
import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Relay;

public class Auger extends Subsystem1816 {
    private Relay auger;
    private AugerDirection direction;
    private DigitalInput augerRotateSwitch;
    private DigitalInput augerTopLimitSwitch;
    private Counter counter;
    
    public Auger(int augerRelay, int augerRotationSwitch, int augerTopSwitch) {
        super("Auger");
        auger = new Relay(augerRelay);
        augerRotateSwitch = new DigitalInput(augerRotationSwitch);
        augerTopLimitSwitch = new DigitalInput(augerTopSwitch);
        counter = new Counter(augerRotateSwitch);
        counter.setSemiPeriodMode(false);
        direction = AugerDirection.AUGER_STOP;
        counter.start();
    }
    
    public void setAugerDirection(AugerDirection direction) {
        this.direction = direction;
        update();
    }
    
    public AugerDirection getAugerDirection() {
        return direction;
    }
    
    public void update() {
        AugerDirection processedDirection = direction;
        if(direction.equals(AugerDirection.AUGER_UP) && getAugerTopLimitSwitch()) {
            processedDirection = AugerDirection.AUGER_STOP;
        }
        auger.set(processedDirection.getRelayValue());
    }
    
    public int getAugerRotationCount() {
        return counter.get();
    }
    
    public boolean getAugerRotateSwitch() {
        return augerRotateSwitch.get();
    }
    
    public boolean getAugerTopLimitSwitch() {
        return augerTopLimitSwitch.get();
    }
    
    public static class AugerDirection {
        public static final AugerDirection AUGER_UP = new AugerDirection((byte)1);
        public static final AugerDirection AUGER_DOWN =  new AugerDirection((byte)-1);
        public static final AugerDirection AUGER_STOP = new AugerDirection((byte)0);
        
        private byte value;
        
        private AugerDirection(byte value){
            this.value = value;
        }
        
        protected byte getValue(){
            return value;
        }
        
        public boolean equals(Object other){
            if(other instanceof AugerDirection){
                return ((AugerDirection)other).getValue() == this.getValue();
            }
            return false;
        }
        
        public int hashCode(){
            return new Byte(getValue()).hashCode();
        }
        
        public Relay.Value getRelayValue(){
            if(equals(AUGER_UP)){
                return Relay.Value.kForward;
            }
            else if(equals(AUGER_DOWN)){
                return Relay.Value.kReverse;
            }
            return Relay.Value.kOff;
        }
    }
}
