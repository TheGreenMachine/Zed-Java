/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.edinarobotics.zed.subsystems;

import com.edinarobotics.utils.subsystems.Subsystem1816;
import edu.wpi.first.wpilibj.Servo;

public class Climber extends Subsystem1816 {
    private Servo climberServo;
    private double position;
    
    public static final double CLIMBER_UNDEPLOYED = 0;
    public static final double CLIMBER_DEPLOYED = 1;
    
    public Climber(int climberServo){
        //this.climberServo = new Servo(climberServo);
        position = 0;
    }
    
    public void setClimberDeployed(boolean deployed){
        setPosition(deployed ? CLIMBER_DEPLOYED : CLIMBER_UNDEPLOYED);
    }
    
    public void setPosition(double position){
        this.position = position;
        update();
    }

    public void update() {
        //climberServo.set(position);
    }
}
