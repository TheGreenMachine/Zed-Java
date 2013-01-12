
package com.edinarobotics.zed;

/**
 *
 * @author Eric
 */

public class Components {
    
    private static Components instance;
    
    
    /**
     * Returns instance of {@link Components}, creating one if null.
     * @return {@link Components}
     */
    public static Components getInstance() {
        return instance == null ? instance = new Components() : instance;
    }
    
}
