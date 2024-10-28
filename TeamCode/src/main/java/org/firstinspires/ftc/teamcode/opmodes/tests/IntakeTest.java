package org.firstinspires.ftc.teamcode.opmodes.tests;

import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.SubsystemsCollection;

@TeleOp(name = "Test - Intake")
public class IntakeTest extends LinearOpMode {
    public void runOpMode() {
        SubsystemsCollection sys = SubsystemsCollection.getInstance(hardwareMap);
        GamepadEx testingGamepad = new GamepadEx(this.gamepad1);

        waitForStart();

        while (opModeIsActive()) {
            sys.intake.setSlidePositions((testingGamepad.getLeftX() + 1.0) / 2);

            if (testingGamepad.gamepad.dpad_left) {
                sys.intake.arm.setTargetPosition(Intake.ArmPositions.EXTAKE);
            } else if (testingGamepad.gamepad.dpad_up) {
                sys.intake.arm.setTargetPosition(Intake.ArmPositions.HOVER);
            } else if (testingGamepad.gamepad.dpad_right) {
                sys.intake.arm.setTargetPosition(Intake.ArmPositions.PICKUP);
            }

            telemetry.addData("Left Extension Position", sys.intake.leftSlide.getPosition());
            telemetry.addData("Right Extension Position", sys.intake.leftSlide.getPosition());
            telemetry.addData("Pivoting Motor Position", sys.intake.arm.getCurrentPosition());
            telemetry.update();
        }
    }


}
