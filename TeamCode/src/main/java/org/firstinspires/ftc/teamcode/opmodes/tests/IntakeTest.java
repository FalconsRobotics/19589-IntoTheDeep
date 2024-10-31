package org.firstinspires.ftc.teamcode.opmodes.tests;

import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.Extake;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.SubsystemsCollection;

@TeleOp(name = "Test - Intake")
public class IntakeTest extends LinearOpMode {
    public void runOpMode() {
        SubsystemsCollection sys = SubsystemsCollection.getInstance(hardwareMap);
        GamepadEx testingGamepad = new GamepadEx(this.gamepad1);

        sys.extake.setArmPosition(Intake.ArmPosition.IDLE);
        waitForStart();

        while (opModeIsActive()) {
            sys.intake.setSlidePosition((testingGamepad.getLeftX() * 90.0));

            if (testingGamepad.gamepad.dpad_left) {
                sys.intake.setArmPosition(Intake.ArmPosition.UNLOAD);
            } else if (testingGamepad.gamepad.dpad_up) {
                sys.intake.setArmPosition(Intake.ArmPosition.HOVER);
            } else if (testingGamepad.gamepad.dpad_right) {
                sys.intake.setArmPosition(Intake.ArmPosition.PICKUP);
            } else {
                sys.intake.setArmPosition(Intake.ArmPosition.IDLE);
            }

            telemetry.addData("Left Extension Position", sys.intake.leftSlide.getPosition());
            telemetry.addData("Right Extension Position", sys.intake.leftSlide.getPosition());
            telemetry.addData("Pivoting Motor Position", sys.intake.arm.getCurrentPosition());
            telemetry.update();

            sys.intake.periodic();
        }
    }
}