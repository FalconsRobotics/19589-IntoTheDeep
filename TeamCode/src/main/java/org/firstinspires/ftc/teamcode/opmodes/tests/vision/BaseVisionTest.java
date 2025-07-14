package org.firstinspires.ftc.teamcode.opmodes.tests.vision;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.DriveBase;
import org.firstinspires.ftc.teamcode.utilities.SubsystemsCollection;

@TeleOp(name = "Vision Test 2")
public class BaseVisionTest extends LinearOpMode {
    private SubsystemsCollection sys;
    private Limelight3A limelight;

    @Override
    public void runOpMode() {
        SubsystemsCollection.deinit();
        sys = SubsystemsCollection.getInstance(hardwareMap);
        limelight = hardwareMap.get(Limelight3A.class, "limelight");

        limelight.pipelineSwitch(2);
        limelight.start();

        waitForStart();

        while(opModeIsActive()) {
            LLResult result = limelight.getLatestResult();

            if (result != null && result.isValid()) {
                double tx = result.getTx();
                double ty = result.getTy();
                double ta = result.getTa();

                sys.driveBase.driveRobotCentric = true;
                sys.driveBase.motorPowers = new Pose2d((1.0 - ta) * 0.75, (1.0 - ty) * 0.75, -(tx * 0.15));

                telemetry.addData("Target Found", "Yes");
                telemetry.addData("tx", tx);
                telemetry.addData("ty", ty);
                telemetry.addData("ta", ta);
            } else {
                sys.driveBase.motorPowers = new Pose2d(0, 0, 0);
                telemetry.addData("Target Found", "No");
            }

            telemetry.update();
        }

        limelight.stop();
    }
}
