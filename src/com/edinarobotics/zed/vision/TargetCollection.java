package com.edinarobotics.zed.vision;

import com.sun.squawk.util.Arrays;
import com.sun.squawk.util.MathUtils;
import com.sun.squawk.util.StringTokenizer;

public class TargetCollection {
    private Target[] targets;
    
    public TargetCollection(String targetData) {
        if(!(targetData.equals(""))) {
            StringTokenizer targetTokenizer = new StringTokenizer(targetData, ":");
            int currentTarget = 0;
            try {
                while(targetTokenizer.hasMoreTokens()) {
                    StringTokenizer targetDataTokenizer = new StringTokenizer(targetTokenizer.nextToken(), ",");
                    double x = Double.parseDouble(targetDataTokenizer.nextToken());
                    double y = Double.parseDouble(targetDataTokenizer.nextToken());
                    double distance = Double.parseDouble(targetDataTokenizer.nextToken());
                    boolean isCenter = targetDataTokenizer.nextToken().equals("1");

                    targets[currentTarget] = new Target(x, y, distance, isCenter);
                    currentTarget++;
                }
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Returns a copy of the internal Target array used by this TargetCollection.
     * @return A Target array equivalent to that used internally by TargetCollection.
     */
    public Target[] getTargets(){
        return (Target[])Arrays.copy(targets);
    }
    
    /**
     * Returns an array of all targets of the specified type.
     * If {@code centerTarget} is {@code true}, this method returns all center
     * (high) goals. If {@code centerTarget} is {@code false}, this method
     * returns all non-center (middle) goals.
     * @param centerTarget A boolean value indicating whether to return
     * high or medium Target objects.
     * @return An array of all targets meeting the specified criteria.
     */
    public Target[] filterByType(boolean centerTarget){
        int count = 0;
        for(int i = 0; i < targets.length; i++){
            Target target = targets[i];
            if(target.isCenter() == centerTarget){
                count++;
            }
        }
        Target[] foundTargets = new Target[count];
        int index = 0;
        for(int i = 0; i < targets.length; i++){
            Target target = targets[i];
            if(target.isCenter() == centerTarget){
                foundTargets[index] = target;
                index++;
            }
        }
        return foundTargets;
    }
    
    /**
     * Returns the closest Target object to the given point. The distance
     * from the point to the center of all Target objects in this TargetCollection
     * is computed and the target with the smallest such distance is returned.
     * @param x The x-coordinate of the point to which the distance is computed.
     * @param y The y-coordinate of the point to which the distance is computed.
     * @return The target with the smallest distance to the specified point
     * or {@code null} if this TargetCollection is empty.
     */
    public Target getClosestTarget(double x, double y){
        double minDistance = Double.MAX_VALUE;
        Target foundTarget = null;
        for(int i = 0; i < targets.length; i++){
            Target target = targets[i];
            double sqDistance = MathUtils.pow(target.getX() - x, 2)+MathUtils.pow(target.getY() - y, 2);
            if(sqDistance < minDistance){
                minDistance = sqDistance;
                foundTarget = target;
            }
        }
        return foundTarget;
    }
    
    /**
     * Returns the closest Target object to the given point and with the given
     * type. The distance from the point to the center of all Target objects of the proper
     * type is computed and the target with the smallest such distance is returned.
     * @param x The x-coordinate of the point to which the distance is computed.
     * @param y The y-coordinate of the point to which the distance is computed.
     * @param centerTarget The type of target to return. {@code true} indicates
     * a high, center target and {@code false} indicates a low, non-center target.
     * @return The target with the smallest distance to the specified point
     * or {@code null} if no such target exists.
     */
    public Target getClosestTarget(double x, double y, boolean centerTarget){
        double minDistance = Double.MAX_VALUE;
        Target foundTarget = null;
        for(int i = 0; i < targets.length; i++){
            Target target = targets[i];
            if(target.isCenter() == centerTarget){
                double sqDistance = MathUtils.pow(target.getX() - x, 2)+MathUtils.pow(target.getY() - y, 2);
                if(sqDistance < minDistance){
                    minDistance = sqDistance;
                    foundTarget = target;
                }
            }
        }
        return foundTarget;
    }
}
