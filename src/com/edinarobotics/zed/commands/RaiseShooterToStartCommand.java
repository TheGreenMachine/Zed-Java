package com.edinarobotics.zed.commands;

import com.edinarobotics.zed.subsystems.Lifter;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

public class RaiseShooterToStartCommand extends CommandGroup {
    public RaiseShooterToStartCommand(){
        addSequential(new SetLifterCommand(Lifter.LifterDirection.LIFTER_UP));
        addSequential(new WaitCommand(0.54));
        addSequential(new SetLifterCommand(Lifter.LifterDirection.LIFTER_STOP));
    }
}
