package com.edinarobotics.zed.subsystems;

import com.edinarobotics.utils.subsystems.Subsystem1816;
import edu.wpi.first.wpilibj.Relay;

public class Auger extends Subsystem1816 {
    public static final byte AUGER_UP = 1;
    public static final byte AUGER_DOWN = -1;
    public static final byte AUGER_STOP = 0;
    
    private Relay auger;
    private byte augerDirection;
    
    public Auger(int augerRelay) {
        super("Auger");
        auger = new Relay(augerRelay);
    }

    private Relay.Value getRelayDirection(byte direction) {
        if(direction == 0 ) {
            return Relay.Value.kOff;
        }
        if(direction > 0) {
            return Relay.Value.kForward;
        }
        return Relay.Value.kReverse;
    }
    
    public void setAugerDirection(byte direction) {
        augerDirection = direction;
    }
    
    public void update() {
        auger.set(getRelayDirection(augerDirection));
    }
}
