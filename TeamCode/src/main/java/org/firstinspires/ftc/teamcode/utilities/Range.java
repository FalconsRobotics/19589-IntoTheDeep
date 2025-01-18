package org.firstinspires.ftc.teamcode.utilities;

public class Range {
    /** Returns whether or not the difference of two values is within a specified range. */
    public static boolean withinRange(double v1, double v2, double range) {
        double difference = Math.abs(v1 - v2);
        return difference <= range;
    }

    public static boolean withinRange(float v1, float v2, float range) {
        float difference = Math.abs(v1 - v2);
        return difference <= range;
    }

    public static boolean withinRange(int v1, int v2, int range) {
        int difference = Math.abs(v1 - v2);
        return difference <= range;
    }
}
