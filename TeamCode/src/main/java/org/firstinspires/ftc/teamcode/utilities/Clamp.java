package org.firstinspires.ftc.teamcode.utilities;

/** Holds single, static `clamp()` function. Why doesn't java already have this? */
public class Clamp {
    public static double clamp(double value, double min, double max) {
        return Math.min(max, Math.max(min, value));
    }
}
