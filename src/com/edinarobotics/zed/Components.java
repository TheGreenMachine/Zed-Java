
package com.edinarobotics.zed;

import com.edinarobotics.zed.subsystems.Drivetrain;

public class Components {
    private static Components instance;
    
    //Drivetrain constants
    private static final int FRONT_LEFT_DRIVE_JAGUAR = 1;
    private static final int FRONT_RIGHT_DRIVE_JAGUAR = 2;
    private static final int BACK_LEFT_DRIVE_JAGUAR = 3;
    private static final int BACK_RIGHT_DRIVE_JAGUAR = 4;
    
    //Subsystem objects
    public final Drivetrain drivetrain;
    
    /**
     * Private constructor for the Components singleton. This constructor
     * is only called once and handles creating all the robot subsystems.
     */
    private Components(){
        drivetrain = new Drivetrain(FRONT_LEFT_DRIVE_JAGUAR, FRONT_RIGHT_DRIVE_JAGUAR,
                BACK_LEFT_DRIVE_JAGUAR, BACK_RIGHT_DRIVE_JAGUAR);
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
