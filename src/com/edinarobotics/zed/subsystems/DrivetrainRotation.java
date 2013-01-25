package com.edinarobotics.zed.subsystems;

import com.edinarobotics.utils.commands.MaintainStateCommand;
import com.edinarobotics.utils.subsystems.Subsystem1816;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.command.Command;

public class DrivetrainRotation extends Subsystem1816 implements PIDOutput {
    private double rotation;
    private Drivetrain drivetrain;
    
    public DrivetrainRotation(Drivetrain drivetrain) {
        super("DrivetrainRotation");
        this.drivetrain = drivetrain;
    }
    
    public void mecanumPolarRotate(double rotation) {
        this.rotation = rotation;
        update();
    }
    
    public void update() {
        drivetrain.mecanumPolarRotation(rotation);
    }

    public void initDefaultCommand(){
        setDefaultCommand(new MaintainStateCommand(this));
    }
    
    
    public void setDefaultCommand(Command command){
        if(getDefaultCommand() != null){
            super.getDefaultCommand().cancel();
        }
        super.setDefaultCommand(command);
    }

    public void pidWrite(double d) {
        mecanumPolarRotate(d);
    }
    
}
