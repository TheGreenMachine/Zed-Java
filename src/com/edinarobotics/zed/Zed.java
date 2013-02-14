/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.edinarobotics.zed;


import com.edinarobotics.utils.commands.MaintainStateCommand;
import com.edinarobotics.utils.gamepad.Gamepad;
import com.edinarobotics.utils.pid.PIDTuningManager;
import com.edinarobotics.zed.commands.GamepadDriveRotationCommand;
import com.edinarobotics.zed.commands.GamepadDriveStrafeCommand;
import com.edinarobotics.zed.subsystems.DrivetrainRotation;
import com.edinarobotics.zed.subsystems.DrivetrainStrafe;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

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
        DrivetrainStrafe drivetrainStrafe = Components.getInstance().drivetrainStrafe;
        drivetrainStrafe.setDefaultCommand(new MaintainStateCommand(drivetrainStrafe));
        DrivetrainRotation drivetrainRotation = Components.getInstance().drivetrainRotation;
        drivetrainRotation.setDefaultCommand(new MaintainStateCommand(drivetrainRotation));
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
        Gamepad driveGamepad = Controls.getInstance().gamepad1;
        Gamepad shootGamepad = Controls.getInstance().gamepad2;
        Components.getInstance().drivetrainStrafe.setDefaultCommand(new GamepadDriveStrafeCommand(driveGamepad));
        Components.getInstance().drivetrainRotation.setDefaultCommand(new GamepadDriveRotationCommand(driveGamepad, shootGamepad));
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
    public void testInit() {
        LiveWindow.setEnabled(false);
        teleopInit();
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        teleopPeriodic();
        PIDTuningManager.getInstance().runTuning();
    }
    
}
