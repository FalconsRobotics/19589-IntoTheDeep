package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.arcrobotics.ftclib.hardware.motors.CRServo;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.utilities.ControlConstants;
import org.firstinspires.ftc.teamcode.utilities.controllers.MotorWithController;
import org.firstinspires.ftc.teamcode.utilities.controllers.MotorWithPIDFController;
import org.firstinspires.ftc.teamcode.utilities.controllers.ServoWithController;


/** Manages all mechanisms associated with loading samples. */
public class Intake extends SubsystemBase {
    /** Pre-defined slide positions */
    public static final class SlidePosition { // Wish this could be an enum. Java says: "TOO BAD!"
        // FULLY_RETRACTED should only be used for initialization
        public static final double FULLY_RETRACTED = 1;
        public static final double RETRACTED = 1 - 0.005;
        public static final double EXTENDED = 0.7758;
    }

    /** Pre-defined intake pivoting positions */
    public static final class PivotPosition {
        public static final double RIGHT = 0.15;
        public static final double LEFT = 0.815;
        public static final double MIDDLE = 0.5;
    }

    /** Pre-defined arm positions. */
    public static final class ArmPosition {
        public static final int UNLOAD = -1035;
        public static final int IDLE = -814;
        public static final int HOVER = -305;
        public static final int PICKUP = 0;
    }

    /** Pre-defined power for intake wheels. */
    public static final class WheelPower {
        public static final double LOAD = 1.0;
        public static final double UNLOAD = -0.6;
        public static final double STOP = 0.0;
    }

    /** Servo object for the left and right linear linkage extension servos. Specific class used
     *  for manual control. */
    public final ServoWithController leftSlide, rightSlide;

    /** Servo object for the intake pivot. manual control required in TeleOp. */
    public final ServoWithController pivot;

    /** Servo object for the front and back wheels. */
    public final CRServo frontWheel, backWheel;

    /** Color sensor used to determine the color of a loaded sample. */
    public final ColorSensor sampleColor;

    /** Motor object for the pivoting arm. This should be used explicitly with target positions and
     *  not power. */
    public final MotorWithController arm;

    /** Initializes all members using 'map.' */
    public Intake(HardwareMap map) {
        leftSlide = new ServoWithController(map, "Intake-LeftSlide");
        rightSlide = new ServoWithController(map, "Intake-RightSlide");
        setSlidePosition(SlidePosition.FULLY_RETRACTED);

        pivot = new ServoWithController(map, "Intake-Pivot");
        pivot.servo.setPosition(PivotPosition.MIDDLE);

        sampleColor = null;
//        sampleColor = map.get(ColorSensor.class, "Intake-SampleColor"); // TODO
//        sampleColor.enableLed(true);

        arm = new MotorWithPIDFController(
                map, "Intake-Arm", Motor.GoBILDA.RPM_84,
                new PIDFController(
                    ControlConstants.IntakeArm.KP,
                    ControlConstants.IntakeArm.KI,
                    ControlConstants.IntakeArm.KD,
                    ControlConstants.IntakeArm.KF
                ),
                ControlConstants.IntakeArm.TOLERANCE,
                ControlConstants.IntakeArm.MAX_POWER
        );
        arm.setTarget(ArmPosition.IDLE);

        frontWheel = new CRServo(map, "Intake-FrontWheel");
        backWheel = new CRServo(map, "Intake-BackWheel");
        frontWheel.setInverted(true);
    }

    public void periodic() {
        arm.setMotorPower();
        if (arm.atTarget()) {
            // TODO: Another stupid hack, please fix.
            double angle = arm.motor.getCurrentPosition() / arm.motor.getCPR() * Math.PI * 2;
            arm.motor.set(arm.motor.get() * Math.cos(angle) * ControlConstants.IntakeArm.TARGET_MULTIPLIER);
        }
    }

    /** Sets the `position` of both slide servos */
    public void setSlidePosition(double position) {
        leftSlide.servo.setPosition(position);
        rightSlide.servo.setPosition(position);
    }

    /** moves slide servos relative to their last position. */
    public void moveSlidePosition(double offset) {
        leftSlide.moveServoPosition(offset);
        rightSlide.moveServoPosition(offset);
    }

    /** Sets `power` for both wheels */
    public void setWheelPower(double power) {
        frontWheel.set(power);
        backWheel.set(power);
    }
}
