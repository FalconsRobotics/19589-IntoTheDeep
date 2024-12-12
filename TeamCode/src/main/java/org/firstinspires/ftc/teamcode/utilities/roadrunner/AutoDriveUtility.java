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
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystems.DriveBase;
import org.firstinspires.ftc.teamcode.utilities.ControlConstants;

import java.util.Arrays;


// TODO: HELP! So many things could have been implemented wrong here!

/** Utility object for implementing roadrunner into the project. */
public class AutoDriveUtility {
    /** Drive base data required by Road Runner. */
    public final MecanumDriveKinematics mecanumDrive;
    /** Object used to follow user specified path. */
    public final HolonomicPIDVAFollower follower;

    private final TrajectoryVelocityConstraint velocityConstraint;
    private final TrajectoryAccelerationConstraint accelerationConstraint;

    /** Initializes utility using passed `motors` and `odometry` module. */
    public AutoDriveUtility(HardwareMap map, DriveBase driveBase) {
        mecanumDrive = new MecanumDriveKinematics(
                map, driveBase.mDirect, new OdometryLimelightLocalizer(),
                ControlConstants.RoadRunner.KV,
                ControlConstants.RoadRunner.KA,
                ControlConstants.RoadRunner.KS,
                ControlConstants.RoadRunner.TRACK_WIDTH,
                ControlConstants.RoadRunner.WHEEL_BASE,
                ControlConstants.RoadRunner.LATERAL_MULTIPLIER
        );


        PIDCoefficients translation = new PIDCoefficients(
                ControlConstants.RoadRunner.TRANSLATION_KP,
                ControlConstants.RoadRunner.TRANSLATION_KI,
                ControlConstants.RoadRunner.TRANSLATION_KD
        );

        PIDCoefficients heading = new PIDCoefficients(
                ControlConstants.RoadRunner.HEADING_KP,
                ControlConstants.RoadRunner.HEADING_KI,
                ControlConstants.RoadRunner.HEADING_KD
        );

        follower = new HolonomicPIDVAFollower(translation, translation, heading);

        velocityConstraint = new MinVelocityConstraint(Arrays.asList(
                new TranslationalVelocityConstraint(ControlConstants.RoadRunner.MAX_TRANSLATIONAL_VELOCITY),
                new AngularVelocityConstraint(ControlConstants.RoadRunner.MAX_ANGLE_VELOCITY)
        ));

        accelerationConstraint = new ProfileAccelerationConstraint(ControlConstants.RoadRunner.MAX_ACCELERATION);
    }

    /** Stuff to be ran every cycle. */
    public void periodic() {
        DriveSignal signal = follower.update(mecanumDrive.getLocalizer().getPoseEstimate());
        mecanumDrive.setDriveSignal(signal);

        mecanumDrive.updatePoseEstimate();
    }

    /** Sets drive base to follow passed 'path.' */
    public void followPath(Path path) {
        Trajectory trajectory = TrajectoryGenerator.INSTANCE.generateTrajectory(
                path, velocityConstraint, accelerationConstraint
        );

        follower.followTrajectory(trajectory);
    }

    /** Prints pose estimate to telemetry console. */
    public void printPoseEstimate(Telemetry telemetry) {
        telemetry.addLine("== RRDriveUtility Pose Estimate ==");
        telemetry.addData("X (mm)", mecanumDrive.getPoseEstimate().getX());
        telemetry.addData("Y (mm)", mecanumDrive.getPoseEstimate().getY());
        telemetry.addData("Heading (deg)", mecanumDrive.getPoseEstimate().getHeading() * (180 / Math.PI));
        telemetry.addLine();
    }
}