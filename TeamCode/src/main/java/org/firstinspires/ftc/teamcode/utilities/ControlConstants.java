package org.firstinspires.ftc.teamcode.utilities;

import com.acmerobotics.dashboard.config.Config;

/** Constants used for control systems. All are placed here in order to used with FTC Dashboard. */
@Config
public class ControlConstants {
    /** Control constants relating to the robots intake arm. */
    @Config
    public static class IntakeArm {
        // PIDF Controllers may be swapped with feedforward controllers in the future. If/when this
        // happens please be sure to keep these values here anyways.
        @Config
        public static final class PIDF {
            public static double KP = 0.05;
            public static double KI = 0.015;
            public static double KD = 0.003;
            public static double KF = 0.0;
        }

        public static double MAX_POWER = 0.5;
        public static int TOLERANCE = 8;

    }

    /** Control constants relating to the robots extake lift. */
    @Config
    public static class ExtakeLift {
        @Config
        public static final class PIDF {
            public static double KP = 0.0605;
            public static double KI = 0.0;
            public static double KD = 0.002;
            public static double KF = 0.0;
        }

        public static double MAX_POWER = 0.9;
        public static int TOLERANCE = 20;
    }

    /** Constants to be used with Road Runner. */
    @Config
    public static class RoadRunner {
        @Config
        public static final class Translation {
            public static double KP = 0.1;
            public static double KI = 0.0;
            public static double KD = 0.02;
        }

        @Config
        public static class Heading {
            public static double KP = 0.0;
            public static double KI = 0.0;
            public static double KD = 0.0;
        }

        @Config
        public static class Feedforward {
            public static double KV = 0.00;
            public static double KA = 0.00;
            // Not in line with typical naming conventions here... ?
            public static double K_STATIC = 0.0;
        }

        // TODO: Find these values, as well as the units Road Runner expects them in.
        //  (likely inches) (because of how I've done things maybe millimeters)
        public static double TRACK_WIDTH = 285;
        public static double WHEEL_BASE = 254; // Double check these!
        public static double LATERAL_MULTIPLIER = 1.1;

        public static double MAX_TRANSLATIONAL_VELOCITY = 40.0;
        public static double MAX_ANGLE_VELOCITY = 0.7854;

        public static double MAX_ACCELERATION = 50;
    }
}
