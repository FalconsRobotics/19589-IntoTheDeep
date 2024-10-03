package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.hardware.limelightvision.LLStatus;
import com.qualcomm.hardware.limelightvision.LLResult;

import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;

import org.firstinspires.ftc.teamcode.subsystems.DriveBase;

@TeleOp
public class LimelightTest extends LinearOpMode {

    public void printStatus(LLStatus status) {
        telemetry.addData("LL - Status", "Temp - " + status.getTemp());
        telemetry.addData("LL - Status", "FPS - " + status.getFps());
        telemetry.addData("LL - Status", "Pipeline - " + status.getPipelineIndex() + " [" + status.getPipelineType() + "]");
        telemetry.addLine();
    }

    public void runOpMode() {
        final DriveBase driveBase = new DriveBase(hardwareMap);

        int index = 0;

        final Limelight3A limelight = hardwareMap.get(Limelight3A.class, "limelight");
        limelight.pipelineSwitch(index);

        waitForStart();
        limelight.start();

        while (opModeIsActive() == true) {
            telemetry.update();
            printStatus(limelight.getStatus());

            driveBase.setVelocity(gamepad1.left_stick_y, -gamepad1.left_stick_x, -gamepad1.right_stick_x, 1);

            LLResult latest = limelight.getLatestResult();

            if (gamepad1.a) {
                index = (index += 1) % 10;
                limelight.pipelineSwitch(index);

                sleep(500);
            }

            Pose3D pose = latest.getBotpose();
            telemetry.addData("Pos", "x: " + pose.getPosition().x + " y: " + pose.getPosition().y + "orientation: " + pose.getOrientation().getYaw());
        }
    }
}
