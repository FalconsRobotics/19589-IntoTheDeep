package org.firstinspires.ftc.teamcode.utilities;

import com.acmerobotics.dashboard.config.Config;


/** Constants used for tuning motor controllers. */
public final class ControlConstants {
    /** Control constants relating to the robots intake arm. */
    @Config
    public static final class IntakeArm {
        // PIDF Controllers may be swapped with feedforward controllers in the future. If/when this
        // happens please be sure to keep these values here anyways.
        public static double KP = 0.05;
        public static double KI = 0.015;
        public static double KD = 0.003;
        public static double KF = 0.0;

        public static double MAX_POWER = 0.5;
        public static int TOLERANCE = 8;

    }

    /** Control constants relating to the robots extake lift. */
    @Config
    public static final class ExtakeLift {
        public static double KP = 0.0605;
        public static double KI = 0.0;
        public static double KD = 0.002;
        public static double KF = 0.0;

        public static double MAX_POWER = 0.9;
        public static int TOLERANCE = 20;
    }

    /** Constants relating to drive base rotation in tele-op (independent of Road Runner). */
    @Config
    public static final class DriveBaseRotation {
        // TODO: Implement this
        public static double KP = 0.0;
        public static double KI = 0.0;
        public static double KD = 0.0;
    }

    /** Constants to be used by Road Runner. */
    @Config
    public static final class RoadRunner {
        public static double TRANSLATION_KP = 0.01;
        public static double TRANSLATION_KI = 0.0;
        public static double TRANSLATION_KD = 0.0;

        public static double HEADING_KP = 0.0;
        public static double HEADING_KI = 0.0;
        public static double HEADING_KD = 0.0;

        // TODO: Find these first!
        public static double KV = 0.00;
        public static double KA = 0.00;
        public static double KS = 0.00;

        // TODO: Find these values, as well as the units Road Runner expects them in.
        //  (likely millimeters.) (maybe inches.)
        public static double TRACK_WIDTH = 285;
        public static double WHEEL_BASE = 254; // Double check these!
        public static double LATERAL_MULTIPLIER = 1.1;

        public static double MAX_TRANSLATIONAL_VELOCITY = 40.0;
        public static double MAX_ANGLE_VELOCITY = 0.7854;

        public static double MAX_ACCELERATION = 50;
    }
}
