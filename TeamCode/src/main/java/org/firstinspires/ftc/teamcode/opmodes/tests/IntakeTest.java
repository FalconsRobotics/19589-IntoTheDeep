package org.firstinspires.ftc.teamcode.opmodes.tests;

import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.utilities.SubsystemsCollection;

@TeleOp(name = "Test - Intake")
public class IntakeTest extends LinearOpMode {
    public void runOpMode() {
        SubsystemsCollection sys = SubsystemsCollection.getInstance(hardwareMap);
        GamepadEx testingGamepad = new GamepadEx(this.gamepad1);

        sys.extake.setArmPosition(Intake.ArmPosition.IDLE);
        waitForStart();

        while (opModeIsActive()) {
            if (testingGamepad.gamepad.dpad_left) {
                sys.intake.setArmPosition(Intake.ArmPosition.UNLOAD);
            } else if (testingGamepad.gamepad.dpad_up) {
                sys.intake.setArmPosition(Intake.ArmPosition.HOVER);
            } else if (testingGamepad.gamepad.dpad_right) {
                sys.intake.setArmPosition(Intake.ArmPosition.PICKUP);
            } else {
                sys.intake.setArmPosition(Intake.ArmPosition.IDLE);
            }

            telemetry.addData("Pivoting Motor Position", sys.intake.arm.motor.getCurrentPosition());
            telemetry.addData("Pivoting Motor Power", sys.intake.arm.motor.get());
            telemetry.update();

            sys.intake.periodic();
        }

        sys.deinit();
    }
}