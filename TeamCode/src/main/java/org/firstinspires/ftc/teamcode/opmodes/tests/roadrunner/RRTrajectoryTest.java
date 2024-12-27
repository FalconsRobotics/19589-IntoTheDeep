package org.firstinspires.ftc.teamcode.opmodes.tests.roadrunner;

import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.sun.tools.javac.util.List;

import org.firstinspires.ftc.teamcode.utilities.SubsystemsCollection;
import org.firstinspires.ftc.teamcode.utilities.roadrunner.AutoDriveUtility;

@Autonomous(name = "RRTrajectoryTest")
public class RRTrajectoryTest extends OpMode {
    SubsystemsCollection sys;
    AutoDriveUtility auto;

    public void init() {
        SubsystemsCollection.deinit();
        sys = SubsystemsCollection.getInstance(hardwareMap);

        auto = new AutoDriveUtility(hardwareMap, sys.driveBase);

        auto.runTrajectories(List.of(
                auto.drive.trajectoryBuilder(auto.drive.getPoseEstimate())
                        .splineTo(new Vector2d(25, 0), 0)
                        .splineTo(new Vector2d(0, 0), 180)
                        .build()
        ));
    }

    public void loop() {
        sys.periodic();

        if (!auto.isBusy()) {
            // Will produce an exception. That's fine. I think.
            terminateOpModeNow();
        }
    }
}
