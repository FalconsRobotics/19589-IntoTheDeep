package org.firstinspires.ftc.teamcode.opmodes.tests.vision;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.commands.CommandIntakeSetPivot;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.utilities.roadrunner.VisionControlUtility;

@TeleOp(name = "Find Angle of Block", group = "Tests")
public class FindAnglesOfBlock extends LinearOpMode {
    VisionControlUtility vision = new VisionControlUtility();

    public void runOpMode() {
        waitForStart();
        while (opModeIsActive()) {
            telemetry.addData("Angle:", vision.processCorners());
        }
    }
}