package com.edinarobotics.zed.commands;

import edu.wpi.first.wpilibj.command.Command;

public class ToggleDrivetrainDirectionCommand extends Command {
    public ToggleDrivetrainDirectionCommand() {
    }
    
    protected void initialize() {
        GamepadDriveStrafeCommand.reverseStrafe = !GamepadDriveStrafeCommand.reverseStrafe;
    }

    protected void execute() {
    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {
    }

    protected void interrupted() {
        end();
    }
    
}
