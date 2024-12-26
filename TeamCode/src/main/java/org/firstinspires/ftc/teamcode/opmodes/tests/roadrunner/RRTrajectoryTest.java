package org.firstinspires.ftc.teamcode.opmodes.tests.roadrunner;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.external.rrquickstart.drive.MecanumDriveKinematics;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.utilities.SubsystemsCollection;

@Autonomous(name = "RRTrajectoryTest")
public class RRTrajectoryTest extends OpMode {
    MecanumDriveKinematics drive;
    SubsystemsCollection sys;

    Trajectory forward;
    Trajectory backward;

    public void init() {
        SubsystemsCollection.deinit();
        sys = SubsystemsCollection.getInstance(hardwareMap);
        sys.intake.arm.setTarget(Intake.ArmPosition.IDLE);

        drive = new MecanumDriveKinematics(hardwareMap);

        forward = drive.trajectoryBuilder(new Pose2d())
                .forward(25)
                .build();

        backward = drive.trajectoryBuilder(forward.end())
                .back(25)
                .build();
    }

    @Override
    public void loop() {
//        sys.intake.arm.setTarget(Intake.ArmPosition.IDLE);

        drive.followTrajectory(forward);
        drive.followTrajectory(backward);

        sys.intake.periodic();
    }
}
