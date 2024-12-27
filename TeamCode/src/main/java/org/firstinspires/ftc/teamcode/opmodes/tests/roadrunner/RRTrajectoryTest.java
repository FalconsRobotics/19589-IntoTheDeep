package org.firstinspires.ftc.teamcode.opmodes.tests.roadrunner;

import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.sun.tools.javac.util.List;

import org.firstinspires.ftc.teamcode.external.rrquickstart.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.external.rrquickstart.trajectorysequence.TrajectorySequenceBuilder;
import org.firstinspires.ftc.teamcode.utilities.SubsystemsCollection;
import org.firstinspires.ftc.teamcode.utilities.roadrunner.AutoDriveUtility;

@Autonomous(name = "RRTrajectoryTest")
public class RRTrajectoryTest extends OpMode {
    SubsystemsCollection sys;
    AutoDriveUtility autoDrive;

    public void init() {
        SubsystemsCollection.deinit();
        sys = SubsystemsCollection.getInstance(hardwareMap);

        autoDrive = new AutoDriveUtility(hardwareMap, sys.driveBase);

        autoDrive.runTrajectorySequences(
                autoDrive.trajectorySequenceBuilder()
                        .forward(25)
                        .back(25)
                        .build()
        );
    }

    public void loop() {
        sys.periodic();

        if (!autoDrive.isBusy()) {
            // Will produce an exception. That's fine. I think.
            terminateOpModeNow();
        }
    }
}
