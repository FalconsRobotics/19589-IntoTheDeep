package org.firstinspires.ftc.teamcode.opmodes.tests.roadrunner;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.path.PathBuilder;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.utilities.SubsystemsCollection;
import org.firstinspires.ftc.teamcode.utilities.roadrunner.AutoDriveUtility;


@TeleOp(name = "Test - Road Runner - Rotation", group = "Road Runner")
public class RotationTest extends LinearOpMode {
    public void runOpMode() {
        SubsystemsCollection.deinit();
        SubsystemsCollection sys = SubsystemsCollection.getInstance(hardwareMap);

        sys.driveBase.brake(true);
        AutoDriveUtility driveUtil = new AutoDriveUtility(hardwareMap, sys.driveBase);

        waitForStart();

        driveUtil.followPath(
                new PathBuilder(new Pose2d(0.0, 0.0, 0.0))
                        .lineToLinearHeading(new Pose2d(0.0, 0.0, Math.PI))
                        .build()
        );

        while (opModeIsActive()) {
            sys.periodic();
            driveUtil.periodic();
        }
    }
}
