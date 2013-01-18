
package com.edinarobotics.zed;

public class Components {
    private static Components instance;
    
    /**
     * Returns a new instance of {@link Components}, creating one if null.
     * @return {@link Components}
     */
    public static Components getInstance() {
        return instance == null ? instance = new Components() : instance;
    }
    
}
