package org.firstinspires.ftc.teamcode.opmodes.tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.Extake;
import org.firstinspires.ftc.teamcode.utilities.SubsystemsCollection;

@TeleOp(name = "Test - Extake", group = "Test")
public class ExtakeTest extends LinearOpMode {
    public void runOpMode() {
        SubsystemsCollection.deinit();
        SubsystemsCollection sys = SubsystemsCollection.getInstance(hardwareMap);
        waitForStart();
        sys.extake.setArmPosition(Extake.ArmPosition.UNLOAD);

        while (opModeIsActive()) {
            if (gamepad1.a) {
               sys.extake.setLiftPosition(Extake.LiftPosition.TOP_BUCKET);
            } else if (gamepad1.x) {
                sys.extake.setLiftPosition(Extake.LiftPosition.LOWER_BAR);
            } else if (gamepad1.y) {
                sys.extake.setLiftPosition(Extake.LiftPosition.TOP_BAR);
            } else if (gamepad1.b) {
                sys.extake.setLiftPosition(Extake.LiftPosition.DOWN);
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
