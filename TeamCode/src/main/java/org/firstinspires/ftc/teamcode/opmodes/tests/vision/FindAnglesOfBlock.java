package org.firstinspires.ftc.teamcode.opmodes.tests.vision;

import com.qualcomm.hardware.limelightvision.LLStatus;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.utilities.vision.VisionUtility;

@Disabled
@TeleOp(name = "Find Angle of Block", group = "Tests")
public class FindAnglesOfBlock extends LinearOpMode {
    public void runOpMode() {
        VisionUtility vision = new VisionUtility(hardwareMap);
        waitForStart();
        while (opModeIsActive()) {
            LLStatus status = vision.limelight.getStatus();
            telemetry.addData("Name", "%s", status.getName());
            telemetry.addData("Pipeline", "Index: %d, Type: %s", status.getPipelineIndex(), status.getPipelineType());

           //  telemetry.addData("Angle:", vision.findBlockAngle(0));
            telemetry.update();
        }
    }
}