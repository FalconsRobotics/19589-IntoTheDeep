package org.firstinspires.ftc.teamcode.opmodes.tests;

import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.SubsystemsCollection;

@TeleOp(name = "Test - Intake")
public class IntakeTest extends LinearOpMode {
    public void runOpMode() {
        SubsystemsCollection sys = SubsystemsCollection.getInstance(hardwareMap);
        GamepadEx gamepad1 = new GamepadEx(this.gamepad1);

        waitForStart();

        while (opModeIsActive()) {
            sys.intake.setSlidePositions((gamepad1.getLeftX() + 1.0) / 2);

            if (gamepad1.gamepad.dpad_left) {
                sys.intake.rotateToHandoff();
            } else if (gamepad1.gamepad.dpad_up) {
                sys.intake.rotateToHover();
            } else if (gamepad1.gamepad.dpad_right) {
                sys.intake.rotateToPickup();
            }

            telemetry.addData("Left Extension Position", sys.intake.leftExtensionServo.getPosition());
            telemetry.addData("Right Extension Position", sys.intake.leftExtensionServo.getPosition());
            telemetry.addData("Pivoting Motor Position", sys.intake.pivotingArmMotor.getCurrentPosition());
            telemetry.update();
        }
    }


}
