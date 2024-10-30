package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class Extake extends SubsystemBase {
    private static final int ARM_MAX_ANGLE = 255;

    /** Pre-defined arm positions. */
    public static class ArmPosition {
        public static final double LOAD = ARM_MAX_ANGLE * 0.94;
        public static final double UNLOAD = ARM_MAX_ANGLE * 0.31;
    }

    /** Pre-defined lift positions. */
    public static class LiftPosition {
        public static final int DOWN = 0;
        public static final int UP = 1215;

        public static final int TOP_BUCKET = UP;
        public static final int LOWER_BUCKET = 200;

        public static final int TOP_BAR = 1050;
        public static final int TOP_BAR_UNLOAD = 550;
        public static final int LOWER_BAR = 200;
    }

    /** Motor controlling tube slide. */
    public final MotorEx lift;

    /** Left and right servos controlling the bucket arm. */
    public final SimpleServo leftArm, rightArm;

    public Extake(HardwareMap map) {
        lift = new MotorEx(map, "Extake-Lift", Motor.GoBILDA.RPM_435);
        lift.setRunMode(Motor.RunMode.PositionControl);
        lift.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        lift.setPositionCoefficient(1.0);
        lift.setTargetPosition(LiftPosition.DOWN);

        leftArm = new SimpleServo(map, "Extake-LeftArm", 0, 255, AngleUnit.DEGREES);
        rightArm = new SimpleServo(map, "Extake-RightArm", 0, 255, AngleUnit.DEGREES);
        setArmPosition(ArmPosition.LOAD);
        // Servo face opposite directions, thankfully this was addressed in servo programming.
    }

    public void periodic() {
        // Do nothing for now.
    }

    /** Sets `rotation` of both arm servos. */
    public void setArmPosition(double rotation) {
        leftArm.setPosition(rotation);
        rightArm.setPosition(rotation);
    }
}
