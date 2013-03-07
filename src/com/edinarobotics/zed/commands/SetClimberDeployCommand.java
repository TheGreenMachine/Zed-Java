package com.edinarobotics.zed.commands;

import com.edinarobotics.zed.Components;
import com.edinarobotics.zed.subsystems.Climber;
import edu.wpi.first.wpilibj.command.Command;

public class SetClimberDeployCommand extends Command {
    private boolean deployed;
    private double delayTime;
    private Climber climber;
    
    public SetClimberDeployCommand(boolean deployed, double delayTime){
        this.deployed = deployed;
        this.delayTime = delayTime;
        this.climber = Components.getInstance().climber;
    }
    
    public SetClimberDeployCommand(boolean deployed){
        this(deployed, 0);
    }

    protected void initialize() {
        
    }

    protected void execute() {
        
    }

    protected boolean isFinished() {
        return timeSinceInitialized() >= delayTime;
    }

    protected void end() {
        climber.setClimberDeployed(deployed);
    }

    protected void interrupted() {
        //Interrupted, delay time is not done. Do not deploy.
    }
}
