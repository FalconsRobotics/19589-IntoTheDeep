package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.arcrobotics.ftclib.hardware.ServoEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class Intake extends SubsystemBase {
    public final ServoEx leftExtensionServo;
    public final ServoEx rightExtensionServo;

    public final MotorEx pivotingArmMotor;

    public final ServoEx frontIntakeServo;
    public final ServoEx backIntakeServo;

    public Intake(HardwareMap map) {
        leftExtensionServo = new SimpleServo(map, "Intake-LeftExtension", 0, 360, AngleUnit.DEGREES);
        rightExtensionServo = new SimpleServo(map, "Intake-LeftExtension", 0, 360, AngleUnit.DEGREES);

        pivotingArmMotor = new MotorEx(map, "Intake-PivotingArm", Motor.GoBILDA.RPM_84);

        frontIntakeServo = new SimpleServo(map, "Intake-FrontIntakeWheel", 0, 360, AngleUnit.DEGREES);
        backIntakeServo = new SimpleServo(map, "Intake-BackIntakeWheel", 0, 360, AngleUnit.DEGREES);
    }

    public void periodic() {
        // Do nothing... for now...
    }
}
