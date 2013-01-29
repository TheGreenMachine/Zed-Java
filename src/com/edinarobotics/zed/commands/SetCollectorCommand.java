package com.edinarobotics.zed.commands;

import com.edinarobotics.zed.Components;
import com.edinarobotics.zed.subsystems.Collector;
import edu.wpi.first.wpilibj.command.Command;

public class SetCollectorCommand extends Command {
    private Collector collector;
    private byte direction;
    
    public SetCollectorCommand(byte direction) {
        super("SetCollector");
        this.collector = Components.getInstance().collector;
        this.direction = direction;
        requires(collector);
    }

    protected void initialize() {
        collector.setCollectorDirection(direction);
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
