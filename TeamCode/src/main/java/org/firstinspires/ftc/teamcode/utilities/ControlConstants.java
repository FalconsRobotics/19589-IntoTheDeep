package org.firstinspires.ftc.teamcode.utilities;

import com.acmerobotics.dashboard.config.Config;


/** Constants used for tuning motor controllers. */
public final class ControlConstants {
    /** Control constants relating to the robots intake arm. */
    @Config
    public static final class IntakeArm {
        // PID(F) Controllers may be swapped with feedforward controllers in the future. If/when this
        // happens please be sure to keep these values here anyways.
        public static double KP = 0.0038;
        public static double KI = 0.0;
        public static double KD = 0.00008;

        public static double MAX_POWER = 0.865;
        public static int TOLERANCE = 15;

        public static double TARGET_MULTIPLIER = 0.12; // 0.2 worked best when not using cosine.
    }

    /** Control constants relating to the robots extake lift. */
    @Config
    public static final class ExtakeLift {
        public static double KP = 0.06051;
        public static double KI = 0.0;
        public static double KD = 0.0021325;
        public static double KF = -0.008;

        public static double MAX_POWER = 1.0;
        public static int TOLERANCE = 20;

        public static double TARGET_MULTIPLIER = 0.25;
    }

    /** Constants relating to drive base rotation in tele-op (independent of Road Runner). */
    @Config
    public static final class DriveUtil {
        public static double TRANSLATION_KP = 0.0;
        public static double TRANSLATION_KI = 0.0;
        public static double TRANSLATION_KD = 0.0;

        public static double ROTATION_KP = 0.73;
        public static double ROTATION_KI = 0.0;
        public static double ROTATION_KD = 0.06;

        public static double STRAFE_MULTIPLIER = 1.1;

        public static double MAX_POWER = 0.5;
        public static double TOLERANCE = 2.0; // mm
    }

    /** Constants to be used by Road Runner. */
    @Config
    public static final class RoadRunner {
        public static double TRANSLATION_KP = 8.0;
        public static double TRANSLATION_KI = 0.0;
        public static double TRANSLATION_KD = 0.0;

        public static double HEADING_KP = 10.0;
        public static double HEADING_KI = 0.0;
        public static double HEADING_KD = 0.0;

        public static double KV = 0.01258;
        public static double KA = 0.00009;
        public static double KS = 0.06541;


        public static double TRACK_WIDTH = 18.46;
        public static double WHEEL_BASE = 10; // Double check these!
        public static double LATERAL_MULTIPLIER = 1.08;

        public static double MAX_TRANSLATIONAL_VELOCITY = 30;
        public static double MAX_ANGLE_VELOCITY = Math.PI / 3;

        public static double MAX_ACCELERATION = 30;
    }
}
