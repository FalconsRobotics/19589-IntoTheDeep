package org.firstinspires.ftc.teamcode.opmodes.tests;

import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.SubsystemsCollection;

@TeleOp(name = "Test - Extake")
public class ExtakeTest extends LinearOpMode {
    public void runOpMode() {
        SubsystemsCollection sys = SubsystemsCollection.getInstance(hardwareMap);
        GamepadEx pad = new GamepadEx(gamepad1);

        waitForStart();

        while (opModeIsActive()) {
            // TODO
        }
    }
}
