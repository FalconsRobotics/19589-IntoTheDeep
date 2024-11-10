package org.firstinspires.ftc.teamcode.opmodes.tests.roadrunner;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.path.PathBuilder;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.utilities.SubsystemsCollection;
import org.firstinspires.ftc.teamcode.utilities.roadrunner.RRDriveUtility;

@Autonomous(name = "Test - Straight Line", group = "Road Runner")
public class StraightLineTest extends LinearOpMode {
    public void runOpMode() {
        SubsystemsCollection.deinit();
        SubsystemsCollection sys = SubsystemsCollection.getInstance(hardwareMap);

        sys.driveBase.brake(true);
        RRDriveUtility driveUtil = new RRDriveUtility(sys.driveBase);

        waitForStart();

        driveUtil.followPath(
                new PathBuilder(new Pose2d(0.0, 0.0, 0.0))
                        .lineTo(new Vector2d(609.6, 0.0))
                        .build()
        );

        while (opModeIsActive()) {
            sys.periodic();
            driveUtil.periodic();
        }
    }
}
