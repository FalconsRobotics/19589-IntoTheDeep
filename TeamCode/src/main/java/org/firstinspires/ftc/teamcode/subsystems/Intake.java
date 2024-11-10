package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.motors.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.utilities.ControlConstants;
import org.firstinspires.ftc.teamcode.utilities.MotorWithController;
import org.firstinspires.ftc.teamcode.utilities.MotorWithPIDFController;


public class Intake extends SubsystemBase {
    /** Pre-defined slide positions */
    public static final class SlidePosition { // Wish this could be an enum. Java says: "TOO BAD!"
        // FULLY_RETRACTED should only be used for initialization
        public static final double FULLY_RETRACTED = 1;
        public static final double RETRACTED = 1 - 0.005;
        public static final double EXTENDED = 0.7758;
    }

    /** Pre-defined arm positions. */
    public static final class ArmPosition {
        public static final int UNLOAD = -615;
        public static final int IDLE = -510;
        public static final int HOVER = -150;
        public static final int PICKUP = 0;
    }

    /** Pre-defined power for intake wheels. */
    public static final class WheelPower {
        public static final double LOAD = 1.0;
        public static final double UNLOAD = -1.0;
        public static final double STOP = 0.0;
    }

    /** Servo object for the left and right linear linkage extension servos. */
    public final Servo leftSlide, rightSlide;
    /** Servo object for the front and back wheels. */
    public final CRServo frontWheel, backWheel;

    /** Motor object for the pivoting arm. This should be used explicitly with target positions and
     *  not power. */
    public final MotorWithController arm;

    /** Initializes all members using 'map.' */
    public Intake(HardwareMap map) {
        // SimpleServo are not actually simpler in like any way.
        leftSlide = map.get(Servo.class, "Intake-LeftSlide");
        rightSlide = map.get(Servo.class, "Intake-RightSlide");
        setSlidePosition(SlidePosition.FULLY_RETRACTED);

        arm = new MotorWithPIDFController(
                map, "Intake-Arm",
                ControlConstants.IntakeArm.PIDF.KP,
                ControlConstants.IntakeArm.PIDF.KI,
                ControlConstants.IntakeArm.PIDF.KD,
                ControlConstants.IntakeArm.PIDF.KF,
                ControlConstants.IntakeArm.TOLERANCE,
                ControlConstants.IntakeArm.MAX_POWER
        );
        setArmPosition(ArmPosition.UNLOAD);

        frontWheel = new CRServo(map, "Intake-FrontWheel");
        backWheel = new CRServo(map, "Intake-BackWheel");
        frontWheel.setInverted(true);
    }

    public void periodic() {
        arm.setMotorPower();
        if (arm.atTarget()) {
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

    /** Sets 'position' (in ticks) of arm. */
    public void setArmPosition(int position) {
        arm.setTarget(position);
    }
}
