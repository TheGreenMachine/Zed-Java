package com.edinarobotics.zed.commands;

import com.edinarobotics.zed.vision.TargetCollection;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class VisionTrackingCommand extends Command {
    private NetworkTable visionTable = NetworkTable.getTable("vision");
    private TargetCollection targetCollection;
    
    protected void initialize() {
        visionTable.putBoolean("vton", true);
    }

    protected void execute() {
        targetCollection = new TargetCollection(visionTable.getString("vtdata"));
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        visionTable.putBoolean("vton", false);
    }

    protected void interrupted() {
        end();
    }
    
}
