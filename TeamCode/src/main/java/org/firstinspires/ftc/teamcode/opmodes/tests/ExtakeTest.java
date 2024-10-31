package org.firstinspires.ftc.teamcode.opmodes.tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.Extake;
import org.firstinspires.ftc.teamcode.subsystems.SubsystemsCollection;

@TeleOp(name = "Test - Extake")
public class ExtakeTest extends LinearOpMode {
    public void runOpMode() {
        SubsystemsCollection sys = SubsystemsCollection.getInstance(hardwareMap);
        waitForStart();

        while (opModeIsActive()) {
            if (gamepad1.a) {
                sys.extake.setLiftPosition(Extake.LiftPosition.TOP_BAR);
            } else if (gamepad1.x) {
                sys.extake.setLiftPosition(Extake.LiftPosition.LOWER_BAR);
            } else if (gamepad1.y) {
                sys.extake.setLiftPosition(Extake.LiftPosition.LOWER_BUCKET);
            }

            telemetry.addData("Lift Position", sys.extake.lift.getCurrentPosition());
            telemetry.addData("Right Servo Position", sys.extake.rightArm.getPosition());
            telemetry.addData("Left Servo Position", sys.extake.leftArm.getPosition());
            telemetry.update();

            sys.periodic();
        }
    }


}
