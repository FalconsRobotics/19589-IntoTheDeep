package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

@Config
public class Extake extends SubsystemBase { ;
    private static final double LIFT_MOTOR_POWER = 0.9;

    /** Pre-defined arm positions. */
    public static class ArmPosition {
        public static final double LOAD = 0.88;
        public static final double UNLOAD = 0.31;
        public static final double PREPARE_UNLOAD = UNLOAD + (LOAD - UNLOAD) / 2;
    }

    /** Pre-defined lift positions. */
    public static class LiftPosition {
        public static final int DOWN = 0;
        public static final int UP = 1215;

        public static final int TOP_BUCKET = UP;
        public static final int LOWER_BUCKET = 200;

        public static final int TOP_BAR = 1050;
        public static final int LOWER_BAR = 375;
    }

    /** Motor controlling tube slide. */
    public final MotorWithController lift;

    /** Left and right servos controlling the bucket arm. */
    public final Servo leftArm, rightArm;

    public static double liftKP = 0.05;
    public static double liftKI = 0.0;
    public static double liftKD = 0.002;
    public static double liftKF = 0.0;
    public static int liftTolerance = 15;

    public Extake(HardwareMap map) {
        lift = new MotorWithController(map, "Extake-Lift", liftKP, liftKI, liftKD, liftKF, liftTolerance, LIFT_MOTOR_POWER);
        setLiftPosition(LiftPosition.DOWN);

        leftArm = map.get(Servo.class, "Extake-LeftArm");
        rightArm = map.get(Servo.class, "Extake-RightArm");
        setArmPosition(ArmPosition.LOAD);
        // Servo face opposite directions, thankfully this was addressed in servo programming.
    }

    public void periodic() {
        lift.setMotorPower();
        if (lift.controller.atSetPoint()) {
            lift.motor.set(lift.motor.get() * 0.25);
        }
    }

    /** Sets `position` of both arm servos. */
    public void setArmPosition(double position) {
        leftArm.setPosition(position);
        rightArm.setPosition(position);
    }

    public void setLiftPosition(int position) {
        lift.controller.setSetPoint(position);
    }
}
