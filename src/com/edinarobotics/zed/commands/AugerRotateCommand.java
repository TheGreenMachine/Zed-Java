package com.edinarobotics.zed.commands;

import com.edinarobotics.zed.Components;
import com.edinarobotics.zed.subsystems.Auger;
import com.edinarobotics.zed.subsystems.Auger.AugerDirection;
import edu.wpi.first.wpilibj.command.Command;

public class AugerRotateCommand extends Command {
    private Auger auger;
    private AugerDirection direction;
    private int startingCount;
    
    public AugerRotateCommand(Auger.AugerDirection direction) {
        super("AugerRotate");
        this.auger = Components.getInstance().auger;
        this.direction = direction;
        setTimeout(2.0);
        requires(auger);
    }
    
    protected void initialize() {
        startingCount = auger.getAugerRotationCount();
        auger.setAugerDirection(direction);
    }

    protected void execute() {
        auger.setAugerDirection(direction);
    }

    protected boolean isFinished() {
        return auger.getAugerRotationCount() > startingCount ||
                isTimedOut() || direction.equals(AugerDirection.AUGER_STOP);
    }

    protected void end() {
        auger.setAugerDirection(Auger.AugerDirection.AUGER_STOP);
    }

    protected void interrupted() {
        end();
    }
    
}
