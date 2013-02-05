package com.edinarobotics.zed.subsystems;

import com.edinarobotics.utils.subsystems.Subsystem1816;
import edu.wpi.first.wpilibj.Relay;

public class Lifter extends Subsystem1816 {
    private LifterDirection direction;
    private Relay lifterRelay;
    
    public Lifter(int lifterRelay) {
        super("Lifter");
        this.lifterRelay = new Relay(lifterRelay);
        direction = LifterDirection.LIFTER_STOP;
    }
    
    public void setLifterDirection(LifterDirection direction){
        this.direction = direction;
        update();
    }
    
    public LifterDirection getLifterDirection(){
        return direction;
    }
    
    public void update() {
        lifterRelay.set(direction.getRelayValue());
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
                return Relay.Value.kForward;
            }
            else if(equals(LIFTER_DOWN)){
                return Relay.Value.kReverse;
            }
            return Relay.Value.kOff;
        }
    }
}
