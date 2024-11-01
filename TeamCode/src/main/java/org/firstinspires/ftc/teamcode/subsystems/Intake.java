package org.firstinspires.ftc.teamcode.subsystems;

import android.transition.Slide;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.motors.CRServo;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
@Config
public class Intake extends SubsystemBase {
    private static final double ARM_MOTOR_POWER = 0.5;

    /** Pre-defined slide positions */
    public static class SlidePosition { // Wish this could be an enum. Java says: "TOO BAD!"
        // FULLY_RETRACTED should only be used for initialization
        public static final double FULLY_RETRACTED = 0;
        public static final double RETRACTED = 0.08;
        public static final double EXTENDED = 0.18;
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
    public final Motor arm;
    public final MotorController armController;

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

        arm = new Motor(map, "Intake-Arm", Motor.GoBILDA.RPM_84);
        armController = new MotorController(arm, armKP, armKI, armKD, armKF, armTolerance, ARM_MOTOR_POWER);
        armController.setTargetPosition(ArmPosition.UNLOAD);

        frontWheel = new CRServo(map, "Intake-FrontWheel");
        backWheel = new CRServo(map, "Intake-BackWheel");
        frontWheel.setInverted(true);
    }

    public void periodic() {
        armController.setMotorPower();
        if (armController.atTarget()) {
            arm.set(arm.get() * 0.2);
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
        armController.setTargetPosition(position);
    }
}
