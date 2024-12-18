package org.firstinspires.ftc.teamcode.opmodes.tests.vision;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.utilities.VisionControlUtility;

@TeleOp(name = "Pivot to Block Test", group = "Tests")
public class PivotToBlock extends LinearOpMode {

    VisionControlUtility vision = VisionControlUtility.getInstance(hardwareMap);

    public void runOpMode() {
        if(VisionControlUtility.processCorners(vision.limelight.get))
    }
}
