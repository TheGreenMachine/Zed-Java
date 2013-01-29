package com.edinarobotics.utils.gamepad;

import edu.wpi.first.wpilibj.buttons.Button;

public class DPadButton extends Button {
    private Gamepad gamepad;
    private boolean isXAxis;
    private byte dPadAxis;
    private static final byte DPAD_UP = 1;
    private static final byte DPAD_DOWN = 2;
    private static final byte DPAD_LEFT = 3;
    private static final byte DPAD_RIGHT = 4;
    
    protected DPadButton(Gamepad gamepad, byte dPadAxis) {
        this.gamepad = gamepad;
        this.dPadAxis = dPadAxis;
    }
    
    public boolean get() {
        if(dPadAxis == DPAD_UP) {
            return gamepad.getDPadY() == 1;
        }
        if(dPadAxis == DPAD_DOWN) {
            return gamepad.getDPadY() == -1;
        }
        if(dPadAxis == DPAD_RIGHT) {
            return gamepad.getDPadX() == 1;
        }
        if(dPadAxis == DPAD_LEFT) {
            return gamepad.getDPadX() == -1;
        }
        return false;
    }
}
