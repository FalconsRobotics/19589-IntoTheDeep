package org.firstinspires.ftc.teamcode.opmodes.tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.Extake;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.utilities.SubsystemsCollection;

@TeleOp(name = "Test - Extake", group = "Tests")
public class ExtakeTest extends LinearOpMode {
    public void runOpMode() {
        SubsystemsCollection.deinit();
        SubsystemsCollection sys = SubsystemsCollection.getInstance(hardwareMap);

        while (opModeInInit()) {
            telemetry.addData("Lift Motor Position", sys.extake.lift.motor.getCurrentPosition());
            telemetry.update();
        }

        waitForStart();
        sys.intake.arm.setTarget(Intake.ArmPosition.IDLE);
        sys.extake.setArmPosition(Extake.ArmPosition.LOAD);

        while (opModeIsActive()) {
            if (gamepad1.a) {
               sys.extake.lift.setTarget(Extake.LiftPosition.TOP_BUCKET);
            } else if (gamepad1.x) {
                sys.extake.lift.setTarget(Extake.LiftPosition.LOWER_BAR);
            } else if (gamepad1.y) {
                sys.extake.lift.setTarget(Extake.LiftPosition.TOP_BAR);
            } else if (gamepad1.b) {
                sys.extake.lift.setTarget(Extake.LiftPosition.DOWN);
            }

            if (gamepad1.dpad_up) {
                sys.extake.setArmPosition(Extake.ArmPosition.UNLOAD);
            } else if (gamepad1.dpad_down) {
                sys.extake.setArmPosition(Extake.ArmPosition.LOAD);
            }

            telemetry.addData("Lift Position", sys.extake.lift.motor.getCurrentPosition());
            telemetry.addData("Lift Power", sys.extake.lift.motor.get());
            telemetry.update();

            sys.periodic();
        }
    }
}
