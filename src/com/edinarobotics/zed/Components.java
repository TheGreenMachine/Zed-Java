package com.edinarobotics.zed;

import com.edinarobotics.zed.subsystems.Auger;
import com.edinarobotics.zed.subsystems.Climber;
import com.edinarobotics.zed.subsystems.Collector;
import com.edinarobotics.zed.subsystems.Conveyor;
import com.edinarobotics.zed.subsystems.Drivetrain;
import com.edinarobotics.zed.subsystems.DrivetrainRotation;
import com.edinarobotics.zed.subsystems.DrivetrainStrafe;
import com.edinarobotics.zed.subsystems.Lifter;
import com.edinarobotics.zed.subsystems.Shooter;

public class Components {
    private static Components instance;
    
    //Digital Input Constants
    //Collector Constants
    private static final int COLLECTOR_UPPER_LIMIT_SWITCH = 12;
    private static final int COLLECTOR_LOWER_LIMIT_SWITCH = 13;
    //Lifter Constants
    private static final int LIFTER_UPPER_LIMIT_SWITCH = 11;
    private static final int LIFTER_LOWER_LIMIT_SWITCH = 10;
    //Auger Constants
    private static final int AUGER_ROTATION_SWITCH = 9;
    private static final int AUGER_TOP_SWITCH = 14;
    //Drivetrain Encoder Constants
    private static final int FRONT_LEFT_A = 3;
    private static final int FRONT_LEFT_B = 4;
    private static final int FRONT_RIGHT_A = 1;
    private static final int FRONT_RIGHT_B = 2;
    
    //Analog Input Constants
    //Lifter Constansts
    private static final int LIFTER_STRING_POT = 2;
    //Drivetrain Constants
    private static final int GYRO = 1;

    //PWM constants
    //Drivetrain constants
    private static final int FRONT_LEFT_DRIVE_JAGUAR = 15;
    private static final int FRONT_LEFT_DRIVE_JAGUAR_MINI = 16;
    private static final int FRONT_RIGHT_DRIVE_JAGUAR = 11;
    private static final int FRONT_RIGHT_DRIVE_JAGUAR_MINI = 12;
    private static final int BACK_LEFT_DRIVE_JAGUAR = 17;
    private static final int BACK_LEFT_DRIVE_JAGUAR_MINI = 18;
    private static final int BACK_RIGHT_DRIVE_JAGUAR = 13;
    private static final int BACK_RIGHT_DRIVE_JAGUAR_MINI = 14;
    //Conveyor constants
    private static final int CONVEYOR_VICTOR = 9;
    //Climber constants
    private static final int CLIMBER_SERVO = 5;
    
    //CAN constants
    //Shooter constants
    private static final int SHOOTER_JAGUAR_FIRST = 2;
    private static final int SHOOTER_JAGUAR_SECOND = 1;
    
    //Relay constants
    //Auger constants
    private static final int AUGER_SPIKE = 8;
    //Lifter constants
    private static final int LIFTER_SPIKE = 3;
    //Collector constants
    private static final int COLLECTOR_LEFT_STAR_SPIKE = 1;
    private static final int COLLECTOR_RIGHT_STAR_SPIKE = 2;
    private static final int COLLECTOR_LIFTER_SPIKE = 4;
    
    //Subsystem objects
    public final Drivetrain drivetrain;
    public final DrivetrainStrafe drivetrainStrafe;
    public final DrivetrainRotation drivetrainRotation;
    public final Shooter shooter;
    public final Lifter lifter;
    public final Collector collector;
    public final Conveyor conveyor;
    public final Auger auger;
    public final Climber climber;
    
    /**
     * Private constructor for the Components singleton. This constructor
     * is only called once and handles creating all the robot subsystems.
     */
    private Components(){
        drivetrain = new Drivetrain(FRONT_LEFT_DRIVE_JAGUAR, FRONT_LEFT_DRIVE_JAGUAR_MINI,
                FRONT_RIGHT_DRIVE_JAGUAR, FRONT_RIGHT_DRIVE_JAGUAR_MINI,
                BACK_LEFT_DRIVE_JAGUAR, BACK_LEFT_DRIVE_JAGUAR_MINI,
                BACK_RIGHT_DRIVE_JAGUAR, BACK_RIGHT_DRIVE_JAGUAR_MINI);
        drivetrainStrafe = new DrivetrainStrafe(drivetrain);
        drivetrainRotation = new DrivetrainRotation(drivetrain, GYRO);
        shooter = new Shooter(SHOOTER_JAGUAR_FIRST, SHOOTER_JAGUAR_SECOND);
        lifter = new Lifter(LIFTER_SPIKE, LIFTER_STRING_POT, LIFTER_UPPER_LIMIT_SWITCH, LIFTER_LOWER_LIMIT_SWITCH);
        collector = new Collector(COLLECTOR_LEFT_STAR_SPIKE, COLLECTOR_RIGHT_STAR_SPIKE,
                COLLECTOR_LIFTER_SPIKE, COLLECTOR_UPPER_LIMIT_SWITCH, COLLECTOR_LOWER_LIMIT_SWITCH);
        conveyor = new Conveyor(CONVEYOR_VICTOR);
        auger = new Auger(AUGER_SPIKE, AUGER_ROTATION_SWITCH, AUGER_TOP_SWITCH);
        climber = new Climber(CLIMBER_SERVO);
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
