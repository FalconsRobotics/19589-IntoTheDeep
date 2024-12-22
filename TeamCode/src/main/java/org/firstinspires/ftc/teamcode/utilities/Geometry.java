package org.firstinspires.ftc.teamcode.utilities;

import org.firstinspires.ftc.robotcore.external.navigation.Position;

/** Utility functions relating to geometrical calculations. Used mainly with vision utility. */
public class Geometry {
    /** Simple class for referencing 2D points, offsets, velocities, etc.*/
    public static class Vector2D {
        public double x, y;
        public Vector2D(double x, double y) { this.x = x; this.y = y; }
    }

    /** Returns angle between two points (in radians). */
    public static double getAngle(Vector2D p1, Vector2D p2) {
        return Math.atan2(p2.y - p1.y, p2.x - p1.x);
    }

    /** Returns squared length between two points, great for comparing lengths :smirk: */
    public static double getLengthSquared(Vector2D p1, Vector2D p2) {
        return Math.pow(p2.x - p1.x, 2) + Math.pow(p2.y - p1.y, 2);
    }

    /** Returns length between two points. */
    public static double getLength(Vector2D p1, Vector2D p2) {
        return Math.sqrt(getLengthSquared(p1, p2));
    }
}
