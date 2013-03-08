package com.edinarobotics.zed.commands;

import com.edinarobotics.zed.subsystems.Conveyor;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

public class ConveyorPulseSequenceCommand extends CommandGroup {
    public ConveyorPulseSequenceCommand(double conveyorOutSpeed, double conveyorInSpeed, double pulseTime){
        addSequential(new SetConveyorCommand(conveyorOutSpeed));
        addSequential(new WaitCommand(pulseTime));
        addSequential(new SetConveyorCommand(conveyorInSpeed));
    }
    
    public ConveyorPulseSequenceCommand(double pulseTime){
        this(Conveyor.CONVEYOR_SHOOT_OUT, Conveyor.CONVEYOR_SHOOT_IN, pulseTime);
    }
    
    public ConveyorPulseSequenceCommand(){
        this(0.2);
    }
}
