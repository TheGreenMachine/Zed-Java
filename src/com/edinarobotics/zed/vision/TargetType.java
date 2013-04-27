package com.edinarobotics.zed.vision;

public class TargetType {
    private byte value;
    private String name;
    
    public static final TargetType MIDDLE_GOAL = new TargetType((byte)0, "MIDDLE_GOAL");
    public static final TargetType HIGH_GOAL = new TargetType((byte)1, "HIGH_GOAL");
    public static final TargetType ANY_GOAL = new TargetType((byte)2, "ANY_GOAL");
    
    private TargetType(byte value, String name){
        this.value = value;
        this.name = name;
    }
    
    private byte getValue(){
        return value;
    }
    
    public int hashCode(){
        return new Byte(value).hashCode();
    }
    
    public boolean equals(Object other){
        if(other instanceof TargetType){
            return ((TargetType)other).getValue() == getValue();
        }
        return false;
    }
    
    public String toString(){
        return name;
    }
}
