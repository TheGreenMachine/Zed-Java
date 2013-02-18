package com.edinarobotics.zed.commands;

import com.edinarobotics.zed.Components;
import com.edinarobotics.zed.subsystems.Auger;
import com.edinarobotics.zed.subsystems.Auger.AugerDirection;
import edu.wpi.first.wpilibj.command.Command;

public class AugerRotateCommand extends Command {
    public static AugerDirection previousDirection = AugerDirection.AUGER_STOP;
    
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
        if(direction.equals(previousDirection) || previousDirection.equals(AugerDirection.AUGER_STOP)) {
            return auger.getAugerRotationCount() > startingCount ||
                    isTimedOut() || direction.equals(AugerDirection.AUGER_STOP);
        } else {
            return auger.getAugerRotationCount() > (startingCount + 1) ||
                    isTimedOut() || direction.equals(AugerDirection.AUGER_STOP);
        }
    }

    protected void end() {
        if(!direction.equals(AugerDirection.AUGER_STOP)) {
            previousDirection = AugerDirection.AUGER_STOP;
        }
        interrupted();
    }

    protected void interrupted() {
        auger.setAugerDirection(Auger.AugerDirection.AUGER_STOP);
    }
    
}
