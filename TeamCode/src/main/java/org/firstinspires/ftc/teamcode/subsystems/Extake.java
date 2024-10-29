package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class Extake extends SubsystemBase {
    /** Motor controlling tube slide. */
    public final MotorEx slide;

    /** Left and right servos controlling the bucket arm. */
    public final SimpleServo leftArm, rightArm;

    public Extake(HardwareMap map) {
        slide = new MotorEx(map, "Extake-Slide", Motor.GoBILDA.RPM_435);
        slide.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        slide.setRunMode(Motor.RunMode.PositionControl);

        leftArm = new SimpleServo(map, "Extake-LeftArm", 0, 360, AngleUnit.DEGREES);
        rightArm = new SimpleServo(map, "Extake-RightArm", 0, 360, AngleUnit.DEGREES);
        // Servo face opposite directions, thankfully this is addressed with servo programming.
    }

    public void periodic() {
        // Do nothing for now.
    }

    /** Sets `rotation` of both arm servos. */
    public void setArmRotation(double rotation) {
        leftArm.setPosition(rotation);
        rightArm.setPosition(rotation);
    }
}
