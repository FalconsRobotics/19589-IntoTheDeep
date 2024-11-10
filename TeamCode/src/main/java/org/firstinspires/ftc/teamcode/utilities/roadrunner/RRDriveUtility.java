package org.firstinspires.ftc.teamcode.utilities.roadrunner;

import com.acmerobotics.roadrunner.control.PIDCoefficients;
import com.acmerobotics.roadrunner.drive.DriveSignal;
import com.acmerobotics.roadrunner.followers.HolonomicPIDVAFollower;
import com.acmerobotics.roadrunner.path.Path;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.acmerobotics.roadrunner.trajectory.TrajectoryGenerator;
import com.acmerobotics.roadrunner.trajectory.constraints.AngularVelocityConstraint;
import com.acmerobotics.roadrunner.trajectory.constraints.MinVelocityConstraint;
import com.acmerobotics.roadrunner.trajectory.constraints.ProfileAccelerationConstraint;
import com.acmerobotics.roadrunner.trajectory.constraints.TrajectoryAccelerationConstraint;
import com.acmerobotics.roadrunner.trajectory.constraints.TrajectoryVelocityConstraint;
import com.acmerobotics.roadrunner.trajectory.constraints.TranslationalVelocityConstraint;

import org.firstinspires.ftc.teamcode.subsystems.DriveBase;
import org.firstinspires.ftc.teamcode.utilities.Constants;

import java.util.Arrays;


// TODO: HELP! So many things could have been implemented wrong here!

/** Utility object for implementing roadrunner into the project. */
public class RRDriveUtility {
    /** Drive base data required by Road Runner. */
    public final RRMecanumDriveImpl mecanumDrive;
    /** Object used to follow user specified path. */
    public final HolonomicPIDVAFollower follower;

    private final TrajectoryVelocityConstraint velocityConstraint;
    private final TrajectoryAccelerationConstraint accelerationConstraint;

    /** Initializes utility using passed `motors` and `odometry` module. */
    public RRDriveUtility(DriveBase driveBase) {
        mecanumDrive = new RRMecanumDriveImpl(
                driveBase.mDirect, new RROdometryPodLocalizer(driveBase.odometry),
                Constants.RoadRunner.KV,
                Constants.RoadRunner.KA,
                Constants.RoadRunner.KS,
                Constants.RoadRunner.TRACK_WIDTH,
                Constants.RoadRunner.WHEEL_BASE,
                Constants.RoadRunner.LATERAL_MULTIPLIER
        );


        PIDCoefficients translation = new PIDCoefficients(
                Constants.RoadRunner.TRANSLATION_KP,
                Constants.RoadRunner.TRANSLATION_KI,
                Constants.RoadRunner.TRANSLATION_KD
        );

        PIDCoefficients heading = new PIDCoefficients(
                Constants.RoadRunner.HEADING_KP,
                Constants.RoadRunner.HEADING_KI,
                Constants.RoadRunner.HEADING_KD
        );

        follower = new HolonomicPIDVAFollower(translation, translation, heading);

        velocityConstraint = new MinVelocityConstraint(Arrays.asList(
                new TranslationalVelocityConstraint(Constants.RoadRunner.MAX_TRANSLATIONAL_VELOCITY),
                new AngularVelocityConstraint(Constants.RoadRunner.MAX_ANGLE_VELOCITY)
        ));

        accelerationConstraint = new ProfileAccelerationConstraint(Constants.RoadRunner.MAX_ACCELERATION);
    }

    /** Sets drive base to follow passed 'path.' */
    public void followPath(Path path) {
        Trajectory trajectory = TrajectoryGenerator.INSTANCE.generateTrajectory(
                path, velocityConstraint, accelerationConstraint
        );

        follower.followTrajectory(trajectory);
    }

    /** Stuff to be ran every cycle. */
    public void periodic() {
        DriveSignal signal = follower.update(mecanumDrive.getLocalizer().getPoseEstimate());
        mecanumDrive.setDriveSignal(signal);

        mecanumDrive.updatePoseEstimate();
    }
}
