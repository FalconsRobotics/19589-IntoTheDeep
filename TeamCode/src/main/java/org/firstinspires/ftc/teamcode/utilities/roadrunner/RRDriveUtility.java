package org.firstinspires.ftc.teamcode.utilities.roadrunner;

import androidx.annotation.NonNull;

import com.acmerobotics.roadrunner.control.PIDCoefficients;
import com.acmerobotics.roadrunner.drive.DriveSignal;
import com.acmerobotics.roadrunner.followers.HolonomicPIDVAFollower;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.path.Path;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.acmerobotics.roadrunner.trajectory.TrajectoryGenerator;
import com.acmerobotics.roadrunner.trajectory.constraints.AngularVelocityConstraint;
import com.acmerobotics.roadrunner.trajectory.constraints.MinVelocityConstraint;
import com.acmerobotics.roadrunner.trajectory.constraints.ProfileAccelerationConstraint;
import com.acmerobotics.roadrunner.trajectory.constraints.TrajectoryAccelerationConstraint;
import com.acmerobotics.roadrunner.trajectory.constraints.TrajectoryVelocityConstraint;
import com.acmerobotics.roadrunner.trajectory.constraints.TranslationalVelocityConstraint;

import org.firstinspires.ftc.teamcode.external.GoBildaPinpointDriver;
import org.firstinspires.ftc.teamcode.utilities.ControlConstants;
import org.firstinspires.ftc.teamcode.utilities.DriveBaseMotors;

import java.util.Arrays;


// TODO: HELP! So many things could have been implemented wrong here!

/** Utility object for implementing roadrunner into the project. */
public class RRDriveUtility {
    public final RRMecanumDriveImpl mecanumDrive;
    public final HolonomicPIDVAFollower follower;

    private final TrajectoryVelocityConstraint velocityConstraint;
    private final TrajectoryAccelerationConstraint accelerationConstraint;

    public RRDriveUtility(DriveBaseMotors motors, GoBildaPinpointDriver odometry) {
        mecanumDrive = new RRMecanumDriveImpl(
                motors, new OdometryPodLocalizer(odometry),
                ControlConstants.RoadRunner.Feedforward.KV,
                ControlConstants.RoadRunner.Feedforward.KA,
                ControlConstants.RoadRunner.Feedforward.K_STATIC,
                ControlConstants.RoadRunner.TRACK_WIDTH,
                ControlConstants.RoadRunner.WHEEL_BASE,
                ControlConstants.RoadRunner.LATERAL_MULTIPLIER
        );


        PIDCoefficients translation = new PIDCoefficients(
                ControlConstants.RoadRunner.Translation.KP,
                ControlConstants.RoadRunner.Translation.KI,
                ControlConstants.RoadRunner.Translation.KD
        );

        PIDCoefficients heading = new PIDCoefficients(
                ControlConstants.RoadRunner.Heading.KP,
                ControlConstants.RoadRunner.Heading.KI,
                ControlConstants.RoadRunner.Heading.KD
        );

        follower = new HolonomicPIDVAFollower(translation, translation, heading);

        velocityConstraint = new MinVelocityConstraint(Arrays.asList(
                new TranslationalVelocityConstraint(ControlConstants.RoadRunner.MAX_TRANSLATIONAL_VELOCITY),
                new AngularVelocityConstraint(ControlConstants.RoadRunner.MAX_ANGLE_VELOCITY)
        ));

        accelerationConstraint = new ProfileAccelerationConstraint(ControlConstants.RoadRunner.MAX_ACCELERATION);
    }

    public void followPath(Path path) {
        Trajectory trajectory = TrajectoryGenerator.INSTANCE.generateTrajectory(
                path, velocityConstraint, accelerationConstraint
        );

        follower.followTrajectory(trajectory);
    }

    public void periodic() {
        DriveSignal signal = follower.update(mecanumDrive.getLocalizer().getPoseEstimate());
        mecanumDrive.setDriveSignal(signal);
    }
}
