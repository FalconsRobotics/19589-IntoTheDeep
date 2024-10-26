package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.arcrobotics.ftclib.hardware.motors.CRServo;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.arcrobotics.ftclib.hardware.ServoEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class Intake extends SubsystemBase {
    /** Pre-defined slide positions */
    public static class SlidePositions { // Wish this could be an enum. Java says "TOO BAD!"
        public static final double RETRACTED = 0.0;
        public static final double EXTENDED = 45.0;
    }

    /** Pre-defined arm positions. */
    public static class ArmPositions {
        public static final int EXTAKE = 0;
        public static final int HOVER = 0;
        public static final int PICKUP = 0;

    }

    /** Servo object for the left and right linear linkage extension servos. */
    public final SimpleServo leftSlide, rightSlide;
    /** Servo object for the front and back wheels. */
    public final CRServo frontWheel, backWheel;

    /** Motor object for the pivoting arm. */
    public final MotorEx arm;

    public Intake(HardwareMap map) {
        leftSlide = new SimpleServo(map, "Intake-LeftSlide", 0, 360, AngleUnit.DEGREES);
        rightSlide = new SimpleServo(map, "Intake-LeftSlide", 0, 360, AngleUnit.DEGREES);

        arm = new MotorEx(map, "Intake-Arm", Motor.GoBILDA.RPM_84);
        arm.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);

        frontWheel = new CRServo(map, "Intake-FrontWheel");
        backWheel = new CRServo(map, "Intake-BackWheel");
        // We want wheels moving in opposing directions in order to pick up and release game pieces.
        backWheel.setInverted(true);
    }

    /** Sets the positions of both slide servos.
     *  @param position Sets the positions of both servos */
    public void setSlidePositions(double position) {
        leftSlide.setPosition(position);
        rightSlide.setPosition(position);
    }

    /** Sets power for both wheels
     */
    public void setWheelPower(double power) {
        frontWheel.set(power);
        backWheel.set(power);
    }

    public void periodic() {
        // Do nothing... for now...
    }
}
