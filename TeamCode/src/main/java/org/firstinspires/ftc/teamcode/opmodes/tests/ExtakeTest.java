package org.firstinspires.ftc.teamcode.opmodes.tests;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.subsystems.SubsystemsCollection;

@TeleOp(name = "Test - Drive Base")
public class ExtakeTest extends LinearOpMode {
    public void runOpMode() {
        SubsystemsCollection sys = SubsystemsCollection.getInstance(hardwareMap);
        waitForStart();

        while (opModeIsActive()) {

            telemetry.addData("Lift Position", sys.extake.slide.getCurrentPosition());
            telemetry.addData("Right Servo Position", sys.extake.rightArm.getPosition());
            telemetry.addData("Left Servo Position", sys.extake.leftArm.getPosition());
            telemetry.update();
        }
    }


}
