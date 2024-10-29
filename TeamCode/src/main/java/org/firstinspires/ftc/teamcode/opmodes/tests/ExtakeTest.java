package org.firstinspires.ftc.teamcode.opmodes.tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.SubsystemsCollection;

@TeleOp(name = "Test - Extake")
public class ExtakeTest extends LinearOpMode {
    public void runOpMode() {
        SubsystemsCollection sys = SubsystemsCollection.getInstance(hardwareMap);
        waitForStart();

        while (opModeIsActive()) {

            telemetry.addData("Lift Position", sys.extake.slide.getCurrentPosition());
            telemetry.addData("Right Servo Position", sys.extake.rightArm.getPosition());
            telemetry.addData("Left Servo Position", sys.extake.leftArm.getPosition());
            telemetry.update();
        }
    }


}
