package org.firstinspires.ftc.teamcode.opmodes.tests.vision;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.utilities.vision.VisionUtility;

@TeleOp(name = "Find Angle of Block", group = "Tests")
public class FindAnglesOfBlock extends LinearOpMode {
    VisionUtility vision = new VisionUtility(hardwareMap);

    public void runOpMode() {
        waitForStart();
        while (opModeIsActive()) {
            telemetry.addData("Angle:", vision.findBlockAngle());
        }
    }
}