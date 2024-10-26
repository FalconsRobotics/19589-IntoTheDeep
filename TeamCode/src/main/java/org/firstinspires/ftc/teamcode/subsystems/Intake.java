package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.arcrobotics.ftclib.hardware.ServoEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class Intake extends SubsystemBase {
    public static class SlidePositions {
        public static double RETRACTED = 0.0;
        public static double EXTENDED = 100.0;
    }

    public static class ArmPositions {
        public static int EXTAKE = 0;
        public static int HOVER = 0;
        public static int PICKUP = 0;

    }

    /**
     * Servo object for the left and right linear linkage extension servos.
     */
    public final ServoEx leftExtensionServo, rightExtensionServo;

    /**
     * Motor object for the pivoting arm.
     */
    public final MotorEx pivotingArmMotor;

    /**
     * Servo object for the front and back wheels.
     */
    public final ServoEx frontIntakeServo, backIntakeServo;

    public Intake(HardwareMap map) {
        leftExtensionServo = new SimpleServo(map, "Intake-LeftExtension", 0, 360, AngleUnit.DEGREES);
        rightExtensionServo = new SimpleServo(map, "Intake-LeftExtension", 0, 360, AngleUnit.DEGREES);

        pivotingArmMotor = new MotorEx(map, "Intake-PivotingArm", Motor.GoBILDA.RPM_84);

        frontIntakeServo = new SimpleServo(map, "Intake-FrontIntakeWheel", 0, 360, AngleUnit.DEGREES);
        backIntakeServo = new SimpleServo(map, "Intake-BackIntakeWheel", 0, 360, AngleUnit.DEGREES);
    }

    /**
     * Sets the positions of both slide servos.
     * @param position Sets the positions of both servos.
     */
    public void moveSlides(double position) {
        leftExtensionServo.setPosition(position);
        rightExtensionServo.setPosition(position);
    }

    /**
     * Sets the pivoting motor to the predefined extake position.
     */
    public void rotateToHandoff() {
        pivotingArmMotor.setTargetPosition(ArmPositions.EXTAKE);
    }

    /**
     * Sets the pivoting motor to the predefined hover position.
     */
    public void rotateToHover() {
        pivotingArmMotor.setTargetPosition(ArmPositions.HOVER);
    }

    /**
     * Sets the pivoting motor to the predefined pickup position.
     */
    public void rotateToPickup() {
        pivotingArmMotor.setTargetPosition(ArmPositions.PICKUP);
    }

    public void periodic() {
        // Do nothing... for now...
    }
}
