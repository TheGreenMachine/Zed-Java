package com.edinarobotics.zed.commands;

import com.edinarobotics.zed.Components;
import com.edinarobotics.zed.subsystems.Auger;
import edu.wpi.first.wpilibj.command.Command;

public class SetAugerCommand extends Command {
    private Auger auger;
    private Auger.AugerDirection direction;
    
    public SetAugerCommand(Auger.AugerDirection direction) {
        super("SetAuger");
        this.auger = Components.getInstance().auger;
        this.direction = direction;
        requires(auger);
    }

    protected void initialize() {
        auger.setAugerDirection(direction);
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
