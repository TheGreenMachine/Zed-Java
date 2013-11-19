package com.edinarobotics.zed.commands;

import com.edinarobotics.utils.joystick.ThreeAxisJoystick;
import com.edinarobotics.zed.Components;
import com.edinarobotics.zed.subsystems.Lifter;
import edu.wpi.first.wpilibj.command.Command;

public class SetLifterCommand extends Command {
    ThreeAxisJoystick joystick;
    private Lifter lifter;
    
    public SetLifterCommand(ThreeAxisJoystick joystick) {
        super("SetLifter");
        this.joystick = joystick;
        this.lifter = Components.getInstance().lifter;
        requires(lifter);
    }

    protected void initialize() {
    }

    protected void execute() {
        double joyHatState = joystick.getHatY();
        if(Math.abs(joyHatState) < 0.15) {
            lifter.setLifterDirection(Lifter.LIFTER_STOP);
        } else if (signum(joyHatState) == 1) {
            lifter.setLifterDirection(Lifter.LIFTER_UP);
        } else if (signum(joyHatState) == -1) {
            lifter.setLifterDirection(Lifter.LIFTER_DOWN);
        } else { // SHOULD NEVER ENTER THIS BLOCK!
            lifter.setLifterDirection(Lifter.LIFTER_STOP);
        }
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    }

    protected void interrupted() {
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
    
}
