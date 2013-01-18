/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.edinarobotics.zed;


import com.edinarobotics.utils.commands.MaintainStateCommand;
import com.edinarobotics.utils.gamepad.Gamepad;
import com.edinarobotics.zed.commands.GamepadDriveCommand;
import com.edinarobotics.zed.subsystems.Drivetrain;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;

public class Zed extends IterativeRobot {
    
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        Components.getInstance(); //Create all robot subsystems.
    }
    
    /**
     * This function is called once at the start of autonomous mode.
     */
    public void autonomousInit(){
        Drivetrain drivetrain = Components.getInstance().drivetrain;
        drivetrain.setDefaultCommand(new MaintainStateCommand(drivetrain));
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }
    
    /**
     * This function is called once at the start of teleop mode.
     */
    public void teleopInit(){
        Drivetrain drivetrain = Components.getInstance().drivetrain;
        Gamepad driveGamepad = Controls.getInstance().gamepad1;
        drivetrain.setDefaultCommand(new GamepadDriveCommand(driveGamepad, drivetrain));
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
    }
    
    /**
     * This function is called once at the start of test mode.
     */
    public void testInit(){
        Drivetrain drivetrain = Components.getInstance().drivetrain;
        Gamepad driveGamepad = Controls.getInstance().gamepad1;
        drivetrain.setDefaultCommand(new GamepadDriveCommand(driveGamepad, drivetrain));
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        Scheduler.getInstance().run();
    }
    
}
