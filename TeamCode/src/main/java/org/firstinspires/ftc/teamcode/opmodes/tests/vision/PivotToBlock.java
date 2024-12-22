package org.firstinspires.ftc.teamcode.opmodes.tests.vision;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.utilities.vision.VisionUtility;

@TeleOp(name = "Pivot to Block Test", group = "Tests")
public class PivotToBlock extends LinearOpMode {
    VisionUtility vision = new VisionUtility(hardwareMap);

    public void runOpMode() {
        waitForStart();

        while (opModeIsActive()) {
            double angle = vision.findBlockAngle();

            if(angle <= 20) {
                telemetry.addData("Angle (<= 20)", angle);
            } else if(angle > 20 && angle < 45) {
                telemetry.addData("Angle (> 20; < 45)", angle);
            } else if(angle >= 45) {
                telemetry.addData("Angle (>= 45)", angle);
            }
        }
    }
}
