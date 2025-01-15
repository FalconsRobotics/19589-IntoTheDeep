package org.firstinspires.ftc.teamcode.opmodes.tests.roadrunner;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.utilities.SubsystemsCollection;
import org.firstinspires.ftc.teamcode.utilities.roadrunner.AutoDriveUtility;

@Autonomous(name = "RRTrajectoryTest")
public class RRTrajectoryTest extends OpMode {
    SubsystemsCollection sys;
    AutoDriveUtility autoDrive;

    public void init() {
        SubsystemsCollection.deinit();
        sys = SubsystemsCollection.getInstance(hardwareMap);

        autoDrive = new AutoDriveUtility(hardwareMap, sys.driveBase, new Pose2d(0, 0, 0));
    }

    public void start() {
        autoDrive.runTrajectorySequence(
                autoDrive.build(autoDrive.trajectorySequenceBuilder()
                        .splineTo(new Vector2d(25, 15), Math.toRadians(135))
        ));
        autoDrive.runTrajectorySequence(
                autoDrive.build(autoDrive.trajectorySequenceBuilder()
                        .splineTo(new Vector2d(0, 0), 0.0)
        ));
    }

    public void loop() {
        sys.periodic();

        autoDrive.printPoseEstimate(telemetry);


        if (!autoDrive.isBusy()) {
            // Will produce an exception. That's fine. I think.
            terminateOpModeNow();
        }
    }
}
