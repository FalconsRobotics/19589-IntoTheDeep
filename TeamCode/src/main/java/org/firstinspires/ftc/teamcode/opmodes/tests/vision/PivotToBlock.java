package org.firstinspires.ftc.teamcode.opmodes.tests.vision;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.commands.CommandIntakeSetPivot;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.utilities.roadrunner.VisionControlUtility;

//import org.firstinspires.ftc.teamcode.utilities.VisionControlUtility;

@TeleOp(name = "Pivot to Block Test", group = "Tests")
public class PivotToBlock extends LinearOpMode {
       VisionControlUtility vision = new VisionControlUtility(hardwareMap);

    public void runOpMode() {
        waitForStart();
        while (opModeIsActive()) {
            if(VisionControlUtility.processCorners() <= 20){
                new CommandIntakeSetPivot(Intake.PivotPosition.MIDDLE);
            } else if(VisionControlUtility.processCorners() > 20 && VisionControlUtility.processCorners() < 45){
                new CommandIntakeSetPivot(Intake.PivotPosition.LEFT);
            } else if(VisionControlUtility.processCorners() >= 45){
                new CommandIntakeSetPivot(Intake.PivotPosition.LEFT);
            }
        }
    }
}
