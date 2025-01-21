package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.controller.PIDController;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.utilities.ControlConstants;
import org.firstinspires.ftc.teamcode.utilities.controllers.MotorWithPIDFController;


/** Manages all mechanisms associated with unloading samples and specimens. */
public class Extake extends SubsystemBase {
    /** Pre-defined bucket arm positions. */
    public static final class BucketPosition {
        public static final double LOAD = 0.89;
        public static final double UNLOAD = 0.31;
        public static final double PREPARE_UNLOAD = 0.75;
    }

    /** Pre-defined lift climb servo positions. */
    public static final class HookPosition {
        public static final double IDLE = 0;
        public static final double PREPARE_HANG = 0;
    }


    /** Pre-defined lift positions. */
    public static final class LiftPosition {
        public static final int DOWN = 35;
        public static final int UP = 1575;

        public static final int TOP_BUCKET = 1433;
        public static final int LOWER_BUCKET = 154;

        public static final int TOP_BAR = 1300;
        public static final int LOWER_BAR = 234;
    }

    /** Motor controlling tube slide. */
    public final MotorWithPIDFController lift;
    /** Secondary motor used to help lift move up and down with the climb. */
    private final Motor liftSecondary;

    /** Left and right servos controlling the bucket arm. */
    public final Servo leftArm, rightArm;

    /** Climb servo */
    public final Servo hook;


    /** Initializes all members using 'map.' */
    public Extake(HardwareMap map) {
        liftSecondary = map.get(Motor.class, "Extake-Lift2");
        lift = new MotorWithPIDFController(
                map, "Extake-Lift", Motor.GoBILDA.RPM_435,
                new PIDController(
                    ControlConstants.ExtakeLift.KP,
                    ControlConstants.ExtakeLift.KI,
                    ControlConstants.ExtakeLift.KD
                ),
                ControlConstants.ExtakeLift.KF,
                ControlConstants.ExtakeLift.TOLERANCE,
                ControlConstants.ExtakeLift.MAX_POWER
        );

        lift.motor.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        liftSecondary.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);

        lift.setTarget(LiftPosition.DOWN);

        leftArm = map.get(Servo.class, "Extake-LeftArm");
        rightArm = map.get(Servo.class, "Extake-RightArm");
        setArmPosition(BucketPosition.LOAD);

        hook = map.get(Servo.class, "Extake-Hook");
    }


    public void periodic() {
        // With two motors, their combined breaking powers should be enough to stop the lift.

        // TODO: Another silly hacky hacky to reduce speedy speedy going downy downy
        if (lift.controller.getSetPoint() < lift.motor.getCurrentPosition()) {
            // Works if kF is designed to keep the robot (roughly) static.
            lift.motor.set((lift.calculateMotorPower() *
                    ControlConstants.ExtakeLift.DOWN_MULTIPLIER *
                    lift.regulator.getPowerMultiplier()));
        } else {
            lift.setMotorPower();
        }

        liftSecondary.set(lift.motor.get());
    }


    /** Sets `position` of both arm servos. */
    public void setArmPosition(double position) {
        leftArm.setPosition(position);
        rightArm.setPosition(position);
    }
}
