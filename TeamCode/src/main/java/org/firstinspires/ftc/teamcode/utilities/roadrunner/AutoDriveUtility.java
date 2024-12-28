package org.firstinspires.ftc.teamcode.utilities.roadrunner;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.external.rrquickstart.drive.MecanumDriveKinematics;
import org.firstinspires.ftc.teamcode.external.rrquickstart.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.external.rrquickstart.trajectorysequence.TrajectorySequenceBuilder;
import org.firstinspires.ftc.teamcode.subsystems.DriveBase;

/** Utility for managing roadrunner for autonomous driving. */
public class AutoDriveUtility {
    // To run all Roadrunner calculations on a different thread.
    private final FollowTrajectoriesThread roadrunner;
    // For trajectory building and following.
    private final MecanumDriveKinematics drive;


    /** Prepares thread to be ran with roadrunner. Note that this will also set the drive base
     *  object to be run using external drive commands, do not change this behaviour willy nilly. */
    public AutoDriveUtility(HardwareMap map, DriveBase driveBase) {
        // Otherwise internal periodic method will override roadrunner drive commands.
        driveBase.useExternalDriveCommands = true;

        drive = new MecanumDriveKinematics(map);
        roadrunner = new FollowTrajectoriesThread(drive);

        // This works because of something, Im not entirely sure as odometry should be updated
        // every loop... If it ain't broke don't fix it, I guess?
        driveBase.odometry.update();
        drive.getLocalizer().setPoseEstimate(new Pose2d(0.0, 0.0, 0.0));
    }


    /** Builder passed to runTrajectorySequences. Start pose is robots current position. */
    public TrajectorySequenceBuilder trajectorySequenceBuilder() {
        return drive.trajectorySequenceBuilder(drive.getPoseEstimate());
    }

    /** Prepares a list of trajectories to be ran on the designated "Roadrunner" thread. If this
     *  thread is currently being used, the calling thread will be halted until it is ready. */
    public void runTrajectorySequence(TrajectorySequence trajectories) {
        roadrunner.addTrajectorySequence(trajectories);

        if (!roadrunner.isAlive()) {
            roadrunner.start();
        }
    }

    /** Whether or not roadrunner thread is currently being used. */
    public boolean isBusy() {
        return roadrunner.isAlive();
    }

    /** Prints pose estimate onto telemetry. */
    public void printPoseEstimate(Telemetry telemetry) {
        Pose2d pose = drive.getPoseEstimate();

        telemetry.addLine("-- Pose Estimate --");
        telemetry.addData("X Position (inches)", pose.getX());
        telemetry.addData("Y Position (inches)", pose.getY());
        telemetry.addData("Heading (degrees)", Math.toDegrees(pose.getHeading()));
    }
}
