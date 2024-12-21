package org.firstinspires.ftc.teamcode.opmodes.tests.vision;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.commands.CommandIntakeSetPivot;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.utilities.roadrunner.VisionControlUtility;

@TeleOp(name = "Pivot to Block Test", group = "Tests")
public class PivotToBlock extends LinearOpMode {
       VisionControlUtility vision = new VisionControlUtility(hardwareMap);

    public void runOpMode() {
        waitForStart();
        while (opModeIsActive()) {
            if(vision.processCorners() <= 20) {
                new CommandIntakeSetPivot(Intake.PivotPosition.MIDDLE);
            } else if(vision.processCorners() > 20 && vision.processCorners() < 45) {
                new CommandIntakeSetPivot(Intake.PivotPosition.LEFT);
            } else if(vision.processCorners() >= 45) {
                new CommandIntakeSetPivot(Intake.PivotPosition.LEFT);
            }
        }
    }
}
