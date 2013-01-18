package com.edinarobotics.zed.subsystems;

import com.edinarobotics.utils.commands.MaintainStateCommand;
import com.edinarobotics.utils.subsystems.Subsystem1816;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Drivetrain extends Subsystem1816 {
    private RobotDrive robotDrive;
    private double magnitude;
    private double direction;
    private double rotation;
    
    public Drivetrain(int frontLeft, int frontRight, int backLeft, int backRight) {
        super("Drivetrain");
        this.robotDrive = new RobotDrive(frontLeft, backLeft, frontRight, backRight);
    }
    
    public void initDefaultCommand(){
        setDefaultCommand(new MaintainStateCommand(this));
    }
    
    
    public void setDefaultCommand(Command command){
        super.setDefaultCommand(command);
    }
    
    public void mecanumPolar(double magnitude, double direction, double rotation){
        this.magnitude = magnitude;
        this.direction = direction;
        this.rotation = rotation;
        update();
    }
    
    public void update(){
        robotDrive.mecanumDrive_Polar(magnitude, direction, rotation);
    }
}
