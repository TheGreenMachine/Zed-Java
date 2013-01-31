package com.edinarobotics.zed;

import com.edinarobotics.zed.subsystems.Collector;
import com.edinarobotics.zed.subsystems.Conveyor;
import com.edinarobotics.zed.subsystems.Drivetrain;
import com.edinarobotics.zed.subsystems.DrivetrainRotation;
import com.edinarobotics.zed.subsystems.DrivetrainStrafe;
import com.edinarobotics.zed.subsystems.Lifter;
import com.edinarobotics.zed.subsystems.Shooter;

public class Components {
    private static Components instance;
    
    //PWM constants
    //Drivetrain constants
    private static final int FRONT_LEFT_DRIVE_JAGUAR = 3;
    private static final int FRONT_RIGHT_DRIVE_JAGUAR = 1;
    private static final int BACK_LEFT_DRIVE_JAGUAR = 2;
    private static final int BACK_RIGHT_DRIVE_JAGUAR = 4;
    //Collector constants
    private static final int COLLECTOR_LEFT_STAR = 5;
    private static final int COLLECTOR_RIGHT_STAR = 6;
    private static final int COLLECTOR_ROLLER = 7;
    //Conveyor constants
    private static final int CONVEYOR = 8;
    
    //CAN constants
    //Shooter constants
    private static final int SHOOTER_JAGUAR_FIRST = 1;
    private static final int SHOOTER_JAGUAR_SECOND = 2;
    //Lifter constants
    private static final int LIFTER_JAGUAR = 3;
    
    //Subsystem objects
    public final Drivetrain drivetrain;
    public final DrivetrainStrafe drivetrainStrafe;
    public final DrivetrainRotation drivetrainRotation;
    public final Shooter shooter;
    public final Lifter lifter;
    public final Collector collector;
    public final Conveyor conveyor;
    
    /**
     * Private constructor for the Components singleton. This constructor
     * is only called once and handles creating all the robot subsystems.
     */
    private Components(){
        drivetrain = new Drivetrain(FRONT_LEFT_DRIVE_JAGUAR, FRONT_RIGHT_DRIVE_JAGUAR,
                BACK_LEFT_DRIVE_JAGUAR, BACK_RIGHT_DRIVE_JAGUAR);
        drivetrainStrafe = new DrivetrainStrafe(drivetrain);
        drivetrainRotation = new DrivetrainRotation(drivetrain);
        shooter = new Shooter(SHOOTER_JAGUAR_FIRST, SHOOTER_JAGUAR_SECOND);
        lifter = new Lifter(LIFTER_JAGUAR);
        collector = new Collector(COLLECTOR_LEFT_STAR, COLLECTOR_RIGHT_STAR, COLLECTOR_ROLLER);
        conveyor = new Conveyor(CONVEYOR);
    }
    
    /**
     * Returns a new instance of {@link Components}, creating one if null.
     * @return {@link Components}
     */
    public static Components getInstance() {
        if(instance == null){
            instance = new Components();
        }
        return instance;
    }
    
}
