package com.edinarobotics.zed.commands;

import com.edinarobotics.zed.Components;
import com.edinarobotics.zed.subsystems.Conveyor;
import edu.wpi.first.wpilibj.command.Command;

public class SetConveyorCommand extends Command {
    private Conveyor conveyor;
    private double velocity;
    
    public SetConveyorCommand(double velocity) {
        super("SetLifter");
        this.conveyor = Components.getInstance().conveyor;
        this.velocity = velocity;
        requires(conveyor);
    }
    
    protected void initialize() {
        conveyor.setConveyorSpeed(velocity);
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
