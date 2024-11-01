package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.motors.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.utilities.MotorWithController;

@Config
public class Intake extends SubsystemBase {
    private static final double ARM_MOTOR_POWER = 0.5;

    /** Pre-defined slide positions */
    public static class SlidePosition { // Wish this could be an enum. Java says: "TOO BAD!"
        // FULLY_RETRACTED should only be used for initialization
        public static final double FULLY_RETRACTED = 1;
        public static final double RETRACTED = 1 - 0.015;
        public static final double EXTENDED = 1 - 0.4;
    }

    /** Pre-defined arm positions. */
    public static class ArmPosition {
        public static final int UNLOAD = -605;
        public static final int IDLE = -510;
        public static final int HOVER = -150;
        public static final int PICKUP = 0;
    }

    /** Servo object for the left and right linear linkage extension servos. */
    public final Servo leftSlide, rightSlide;
    /** Servo object for the front and back wheels. */
    public final CRServo frontWheel, backWheel;

    /** Motor object for the pivoting arm. This should be used explicitly with target positions and
     *  not power. */
    public final MotorWithController arm;

    /** These are for FTC dashboard. DO NOT ALTER. */
    public static double armKP = 0.05;
    public static double armKI = 0.0;
    public static double armKD = 0.003;
    public static double armKF = 0.0;
    public static int armTolerance = 8;

    public Intake(HardwareMap map) {
        // SimpleServo are not actually simpler in like any way.
        leftSlide = map.get(Servo.class, "Intake-LeftSlide");
        rightSlide = map.get(Servo.class, "Intake-RightSlide");
        setSlidePosition(SlidePosition.FULLY_RETRACTED);

        arm = new MotorWithController(map, "Intake-Arm", armKP, armKI, armKD, armKF, armTolerance, ARM_MOTOR_POWER);
        setArmPosition(ArmPosition.UNLOAD);

        frontWheel = new CRServo(map, "Intake-FrontWheel");
        backWheel = new CRServo(map, "Intake-BackWheel");
        frontWheel.setInverted(true);
    }

    public void periodic() {
        arm.setMotorPower();
        if (arm.controller.atSetPoint()) {
            arm.motor.set(arm.motor.get() * 0.2);
        }
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

    public void setArmPosition(int position) {
        arm.controller.setSetPoint(position);
    }
}
