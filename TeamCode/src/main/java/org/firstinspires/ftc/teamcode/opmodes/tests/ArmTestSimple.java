package org.firstinspires.ftc.teamcode.opmodes.tests;

import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.MotorController;

@TeleOp(name = "Arm - Test - Simple")
public class ArmTestSimple extends LinearOpMode {
    public void runOpMode() {
        MotorEx arm = new MotorEx(hardwareMap, "Intake-Arm");
        MotorController armController = new MotorController(arm, 0.001, 50, 0.1);
        armController.setTargetPosition(Intake.ArmPosition.HOVER);

        waitForStart();

        while (opModeIsActive()) {
            if (gamepad1.a) {
                armController.setTargetPosition(Intake.ArmPosition.PICKUP);
            } else if (gamepad1.x) {
                armController.setTargetPosition(Intake.ArmPosition.HOVER);
            } else if (gamepad1.y) {
                armController.setTargetPosition(Intake.ArmPosition.UNLOAD);
            }

            arm.set(armController.getPower());

            telemetry.addData("Motor Position", arm.getCurrentPosition());
            telemetry.addData("at Target", arm.atTargetPosition());
            telemetry.addData("current position", armController.getTargetPosition());
            telemetry.addData("kP", arm.getPositionCoefficient());
            telemetry.update();
        }
    }
}
