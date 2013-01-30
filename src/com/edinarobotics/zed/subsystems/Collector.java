package com.edinarobotics.zed.subsystems;

import com.edinarobotics.utils.commands.MaintainStateCommand;
import com.edinarobotics.utils.subsystems.Subsystem1816;
import edu.wpi.first.wpilibj.Relay;

public class Collector extends Subsystem1816 {
    public static byte COLLECTOR_IN = 1;
    public static byte COLLECTOR_OUT = -1;
    public static byte COLLECTOR_STOP = 0;
    
    private Relay leftStar;
    private Relay rightStar;
    private Relay roller;
    private byte leftStarDirection;
    private byte rightStarDirection;
    private byte rollerDirection;
    
    public Collector(int leftStar, int rightStar, int roller){
        super("Collector");
        this.leftStar = new Relay(leftStar);
        this.rightStar = new Relay(rightStar);
        this.roller = new Relay(roller);
        leftStarDirection = 0;
        rightStarDirection = 0;
        rollerDirection = 0;
    }
    
    protected void initDefaultCommand(){
        setDefaultCommand(new MaintainStateCommand(this));
    }
    
    /**
     * Sets the direction of the collector mechanisms.
     * A positive value will set the collector to a forward direction. Forward
     * is defined as the direction that will bring discs into the robot.<br/>
     * A negative value will set the collector to a backwards direction.<br/>
     * A zero value will stop the collector.
     * @param direction Sets the collector to the direction as given above.
     */
    public void setCollectorDirection(byte direction){
        leftStarDirection = direction;
        rightStarDirection = direction;
        rollerDirection = direction;
        update();
    }
    
    private Relay.Value getRelayDirection(byte direction){
        if(direction == 0){
            return Relay.Value.kOff;
        }
        if(direction > 0){
            return Relay.Value.kForward;
        }
        return Relay.Value.kReverse;
    }
    
    public void update(){
        leftStar.set(getRelayDirection(leftStarDirection));
        rightStar.set(getRelayDirection(rightStarDirection));
        roller.set(getRelayDirection(rollerDirection));
    }
}
