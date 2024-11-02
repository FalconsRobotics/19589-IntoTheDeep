package org.firstinspires.ftc.teamcode.opmodes.tests;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.subsystems.Extake;

@Config
@TeleOp(name = "Entake Arm Position - Test")
public class ExtakeArmPositionTest extends LinearOpMode {
    public static double extakeLoadPosition = Extake.ArmPosition.LOAD;

    public void runOpMode() {
        Servo leftArm = hardwareMap.get(Servo.class, "Extake-LeftArm");
        Servo rightArm = hardwareMap.get(Servo.class, "Extake-RightArm");

        leftArm.setPosition(extakeLoadPosition);
        rightArm.setPosition(extakeLoadPosition);

        waitForStart();

        while (opModeIsActive()) {
            // Do nothing.
        }
    }
}
