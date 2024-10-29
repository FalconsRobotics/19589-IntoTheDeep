package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class Extake extends SubsystemBase {
    /** Predefined arm positions. */
    public static class ArmPosition {
        public static double LOAD = 0.94;
        public static double UNLOAD = 0.31;
    }

    public static class LiftPosition {
        // TODO
    }

    /** Motor controlling tube slide. */
    public final MotorEx lift;

    /** Left and right servos controlling the bucket arm. */
    public final SimpleServo leftArm, rightArm;

    public Extake(HardwareMap map) {
        lift = new MotorEx(map, "Extake-Slide", Motor.GoBILDA.RPM_435);
        lift.setRunMode(Motor.RunMode.PositionControl);
        lift.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        lift.setTargetPosition(0);

        leftArm = new SimpleServo(map, "Extake-LeftArm", 0, 360, AngleUnit.DEGREES);
        rightArm = new SimpleServo(map, "Extake-RightArm", 0, 360, AngleUnit.DEGREES);
        setArmPosition(ArmPosition.LOAD);
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
