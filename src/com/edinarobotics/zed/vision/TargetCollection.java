package com.edinarobotics.zed.vision;

import com.sun.squawk.util.StringTokenizer;

public class TargetCollection {
    private Target[] target;
    private StringTokenizer targetTokenizer;
    private StringTokenizer targetDataTokenizer;
    
    public TargetCollection(String targetData) {
        if(!(targetData.equals(""))) {
            targetTokenizer = new StringTokenizer(targetData, ":");
            int currentTarget = 0;
            try {
                while(targetTokenizer.hasMoreTokens()) {
                    targetDataTokenizer = new StringTokenizer(targetTokenizer.nextToken(), ",");
                    double x = Double.parseDouble(targetDataTokenizer.nextToken());
                    double y = Double.parseDouble(targetDataTokenizer.nextToken());
                    double distance = Double.parseDouble(targetDataTokenizer.nextToken());
                    boolean isCenter = targetDataTokenizer.nextToken().equals("1");

                    target[currentTarget] = new Target(x, y, distance, isCenter);
                    currentTarget++;
                }
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
    
}
