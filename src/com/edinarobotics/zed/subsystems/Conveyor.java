package com.edinarobotics.zed.subsystems;

import com.edinarobotics.utils.subsystems.Subsystem1816;
import edu.wpi.first.wpilibj.Victor;

public class Conveyor extends Subsystem1816 {
    public static final double CONVEYOR_IN = 1;
    public static final double CONVEYOR_OUT = -1;
    public static final double CONVEYOR_STOP = 0;
    
    Victor conveyor;
    double velocity;
    
    public Conveyor(int conveyor){
        super("Conveyor");
        this.conveyor = new Victor(conveyor);
    }
    
    /**
     * Sets the speed of the conveyor belt inside the robot. The values can
     * range from -1.0 to 1.0 and represent the speed of the conveyor.
     * A positive value will set the conveyor to bring discs into the robot
     * at the fastest possible speed. A negative value will remove discs
     * from the robot.
     * @param velocity The new speed of the conveyor belt as defined above.
     */
    public void setConveyorSpeed(double velocity){
        this.velocity = velocity;
        update();
    }
    
    public void update(){
        conveyor.set(velocity);
    }
}
