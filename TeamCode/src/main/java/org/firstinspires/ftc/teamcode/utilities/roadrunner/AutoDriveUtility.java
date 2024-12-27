package org.firstinspires.ftc.teamcode.utilities.roadrunner;

import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.external.rrquickstart.drive.MecanumDriveKinematics;
import org.firstinspires.ftc.teamcode.subsystems.DriveBase;

import java.util.List;

/** Utility for managing roadrunner for autonomous driving. */
public class AutoDriveUtility {
    // To run all Roadrunner calculations on a different thread.
    private final FollowTrajectoriesThread roadrunner;

    /** For trajectory building. DO NOT use for following paths directly, that's what this is
     *  for. */
    public final MecanumDriveKinematics drive;


    /** Prepares thread to be ran with roadrunner. Note that this will also set the drive base
     *  object to be run using external drive commands, do not change this behaviour willy nilly. */
    public AutoDriveUtility(HardwareMap map, DriveBase driveBase) {
        // Otherwise internal periodic method will override roadrunner drive commands.
        driveBase.useExternalDriveCommands = true;

        drive = new MecanumDriveKinematics(map);
        roadrunner = new FollowTrajectoriesThread(drive);
    }


    /** Prepares a list of trajectories to be ran on the designated "Roadrunner" thread. If this
     *  thread is currently being used, the calling thread will be halted until it is ready. */
    public void runTrajectories(List<Trajectory> trajectories) {
        if (roadrunner.isAlive()) {
            // Perhaps I could throw an exception instead? Unsure what I want to do if the user
            // implements this incorrectly
            try {
                roadrunner.join();
            } catch (InterruptedException ignored) {
            }
        }

        roadrunner.addTrajectories(trajectories);
        roadrunner.start();
    }

    /** Whether or not roadrunner thread is currently being used. */
    public boolean isBusy() {
        return roadrunner.isAlive();
    }
}
