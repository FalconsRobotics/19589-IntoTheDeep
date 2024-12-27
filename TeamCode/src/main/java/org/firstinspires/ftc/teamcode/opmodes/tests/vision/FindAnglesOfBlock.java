package org.firstinspires.ftc.teamcode.opmodes.tests.vision;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.utilities.vision.VisionUtility;

@TeleOp(name = "Find Angle of Block", group = "Tests")
public class FindAnglesOfBlock extends LinearOpMode {
    public void runOpMode() {
        VisionUtility vision = new VisionUtility(hardwareMap);
        waitForStart();
        while (opModeIsActive()) {
            telemetry.addData("Angle:", vision.findBlockAngle());
            telemetry.update();
        }
    }
}