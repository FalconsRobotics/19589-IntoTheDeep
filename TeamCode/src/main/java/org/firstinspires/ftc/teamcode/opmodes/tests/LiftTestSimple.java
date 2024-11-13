package org.firstinspires.ftc.teamcode.opmodes.tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.subsystems.Extake;
import org.firstinspires.ftc.teamcode.subsystems.Intake;

@TeleOp(name = "Lift - Test - Simple", group = "Test")
public class LiftTestSimple extends LinearOpMode {
    public void runOpMode() {
        DcMotor lift = hardwareMap.get(DcMotor.class, "Extake-Lift");
        lift.setTargetPosition(Intake.ArmPosition.HOVER);
        lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        waitForStart();

        while (opModeIsActive()) {
            if (gamepad1.a) {
                lift.setTargetPosition(Extake.LiftPosition.LOWER_BAR);
            } else if (gamepad1.x) {
                lift.setTargetPosition(Extake.LiftPosition.LOWER_BUCKET);
            } else if (gamepad1.y) {
                lift.setTargetPosition(Extake.LiftPosition.TOP_BAR);
            }

            telemetry.addData("Motor Position", lift.getCurrentPosition());
            telemetry.addData("Motor Position", lift.getTargetPosition());
            telemetry.update();
        }
    }
}
