package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.arcrobotics.ftclib.hardware.motors.CRServo;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class Intake extends SubsystemBase {
    private static final int SLIDE_MAX_ANGLE = 180;

    /** Pre-defined slide positions */
    public static class SlidePosition { // Wish this could be an enum. Java says: "TOO BAD!"
        public static final double RETRACTED = SLIDE_MAX_ANGLE * 0.0;
        public static final double EXTENDED = SLIDE_MAX_ANGLE * 0.2;
    }

    /** Pre-defined arm positions. */
    public static class ArmPosition {
        public static final int UNLOAD = -655;
        public static final int HOVER = 34;
        public static final int PICKUP = 0;
    }

    /** Servo object for the left and right linear linkage extension servos. */
    public final SimpleServo leftSlide, rightSlide;
    /** Servo object for the front and back wheels. */
    public final CRServo frontWheel, backWheel;

    /** Motor object for the pivoting arm. This should be used explicitly with target positions and
     *  not power. */
    public final MotorEx arm;

    public Intake(HardwareMap map) {
        leftSlide = new SimpleServo(map, "Intake-LeftSlide", 0, SLIDE_MAX_ANGLE, AngleUnit.DEGREES);
        rightSlide = new SimpleServo(map, "Intake-LeftSlide", 0, SLIDE_MAX_ANGLE, AngleUnit.DEGREES);

        arm = new MotorEx(map, "Intake-Arm", Motor.GoBILDA.RPM_84);
        arm.setRunMode(Motor.RunMode.PositionControl);
        arm.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        arm.setPositionCoefficient(1.0);
        arm.setTargetPosition(ArmPosition.UNLOAD);

        frontWheel = new CRServo(map, "Intake-FrontWheel");
        backWheel = new CRServo(map, "Intake-BackWheel");
        // We want wheels moving in opposing directions in order to pick up and release game pieces.
        backWheel.setInverted(true);
    }

    /** Sets the `position` of both slide servos */
    public void setSlidePosition(double position) {
        leftSlide.setPosition(position);
        rightSlide.setPosition(position);
    }

    /** Sets `power` for both wheels */
    public void setWheelPower(double power) {
        frontWheel.set(power);
        backWheel.set(power);
    }

    public void periodic() {
        // Do nothing... for now...
    }
}
