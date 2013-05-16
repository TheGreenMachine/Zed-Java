package com.edinarobotics.zed.commands;

import com.edinarobotics.zed.Components;
import com.edinarobotics.zed.subsystems.Lifter;
import edu.wpi.first.wpilibj.command.Command;

public class SetLifterCommand extends Command {
    private Lifter lifter;
    private double direction;
    
    public SetLifterCommand(double direction) {
        super("SetLifter");
        this.lifter = Components.getInstance().lifter;
        this.direction = direction;
        requires(lifter);
    }

    protected void initialize() {
        lifter.setLifterDirection(direction);
    }

    protected void execute() {
    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
    
}
