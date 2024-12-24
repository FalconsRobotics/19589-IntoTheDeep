package org.firstinspires.ftc.teamcode.opmodes.tests.roadrunner;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.path.PathBuilder;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.utilities.SubsystemsCollection;
import org.firstinspires.ftc.teamcode.utilities.roadrunner.AutoDriveUtility;

@TeleOp(name = "RRTrajectoryTest")
public class RRTrajectoryTest extends OpMode {
    AutoDriveUtility autoDrive;
    SubsystemsCollection sys;

    @Override
    public void init() {
        sys = SubsystemsCollection.getInstance(hardwareMap);
        autoDrive = new AutoDriveUtility(hardwareMap, sys.driveBase);

        autoDrive.followPath(
                new PathBuilder(new Pose2d())
                        .lineTo(new Vector2d(15, 0))
                        .build()
        );
    }

    @Override
    public void loop() {
        autoDrive.periodic();
    }
}
