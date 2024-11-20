package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.utilities.ControlConstants;
import org.firstinspires.ftc.teamcode.utilities.controllers.MotorWithController;
import org.firstinspires.ftc.teamcode.utilities.controllers.MotorWithPIDFController;


public class Extake extends SubsystemBase { ;
    /** Pre-defined arm positions. */
    public static class ArmPosition {
        public static final double LOAD = 0.89;
        public static final double UNLOAD = 0.31;
        public static final double PREPARE_UNLOAD = 0.75;
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

    /** Initializes all members using 'map.' */
    public Extake(HardwareMap map) {
        lift = new MotorWithPIDFController(
                map, "Extake-Lift",
                ControlConstants.ExtakeLift.KP,
                ControlConstants.ExtakeLift.KI,
                ControlConstants.ExtakeLift.KD,
                ControlConstants.ExtakeLift.KF,
                ControlConstants.ExtakeLift.TOLERANCE,
                ControlConstants.ExtakeLift.MAX_POWER
        );
        lift.setTarget(LiftPosition.DOWN);

        leftArm = map.get(Servo.class, "Extake-LeftArm");
        rightArm = map.get(Servo.class, "Extake-RightArm");
        setArmPosition(ArmPosition.LOAD);
        // Servo face opposite directions, thankfully this was addressed in servo programming.
    }

    public void periodic() {
        lift.setMotorPower();
        if (lift.atTarget()) {
            lift.motor.set(lift.motor.get() * 0.25);
        }
    }

    /** Sets `position` of both arm servos. */
    public void setArmPosition(double position) {
        leftArm.setPosition(position);
        rightArm.setPosition(position);
    }
}
