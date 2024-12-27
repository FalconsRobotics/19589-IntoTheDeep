package org.firstinspires.ftc.teamcode.utilities.roadrunner;

import com.acmerobotics.roadrunner.trajectory.Trajectory;

import org.firstinspires.ftc.teamcode.external.rrquickstart.drive.MecanumDriveKinematics;
import org.firstinspires.ftc.teamcode.external.rrquickstart.trajectorysequence.TrajectorySequence;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/** For following roadrunner trajectories on a separate thread. */
public class FollowTrajectoriesThread extends Thread {
    private final Queue<TrajectorySequence> trajectories;
    private final MecanumDriveKinematics mecanumDrive;


    public FollowTrajectoriesThread(MecanumDriveKinematics mecanumDrive) {
        trajectories = new LinkedList<>();
        this.mecanumDrive = mecanumDrive;
    }


    public void run() {
        while (!trajectories.isEmpty()) {
            mecanumDrive.followTrajectorySequence(trajectories.poll());
        }
    }

    public void addTrajectorySequence(TrajectorySequence newTrajectory) {
        this.trajectories.add(newTrajectory);
    }
}
