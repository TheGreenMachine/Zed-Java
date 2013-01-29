package com.edinarobotics.zed.commands;

import com.edinarobotics.zed.Components;
import com.edinarobotics.zed.subsystems.Shooter;
import edu.wpi.first.wpilibj.command.Command;

public class SetShooterCommand extends Command {
    private Shooter shooter;
    private double velocity;
    
    public SetShooterCommand(double velocity) {
        super("SetShooter");
        this.shooter = Components.getInstance().shooter;
        this.velocity = velocity;
        requires(shooter);
    }

    protected void initialize() {
        shooter.setShooterVelocity(velocity);
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
