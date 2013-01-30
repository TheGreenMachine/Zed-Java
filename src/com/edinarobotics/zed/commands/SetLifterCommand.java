package com.edinarobotics.zed.commands;

import com.edinarobotics.zed.Components;
import com.edinarobotics.zed.subsystems.Lifter;
import edu.wpi.first.wpilibj.command.Command;

public class SetLifterCommand extends Command {
    private Lifter lifter;
    private double position;
    
    public SetLifterCommand(double position) {
        super("SetLifter");
        this.lifter = Components.getInstance().lifter;
        this.position = position;
        requires(lifter);
    }

    protected void initialize() {
        lifter.setLifterPosition(position);
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
