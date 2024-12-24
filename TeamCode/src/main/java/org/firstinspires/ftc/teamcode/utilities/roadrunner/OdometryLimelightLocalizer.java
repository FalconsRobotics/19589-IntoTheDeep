package org.firstinspires.ftc.teamcode.utilities.roadrunner;

import androidx.annotation.NonNull;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.localization.Localizer;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.utilities.SubsystemsCollection;
import org.jetbrains.annotations.NotNull;


// TODO: CONVERT MM TO INCHES EVERYWHERE!

/** Formats odometry data to be used with Road Runner, attempts to correct position using
 *  limelight. */
public class OdometryLimelightLocalizer implements Localizer {
    // For getting odometry.
    private final SubsystemsCollection sys;

    // Used to convert to inches as roadrunner supposedly works best with those units.
    private final double INCH_TO_MM = 25.4;
    private final double MM_TO_INCH = 1 / INCH_TO_MM;

    /** @note This class does not manage any initialization procedures relating to the odometry
     *  module */
    public OdometryLimelightLocalizer() {
        sys = SubsystemsCollection.getInstance(null);
    }

    /** Should be ran every cycle. */
    public void update() {
        // No need to update as that is already done in the drive base subsystem.
        // odometryModule.update();
//        if (limelight.getStatus().getPipelineIndex() != VisionUtil.Pipeline.APRILTAGS) return;
//
//        double heading = getHeadingDegrees();
//        limelight.updateRobotOrientation(heading);
//
//        LLResult result = limelight.getLatestResult();
//        if (result == null || !result.isValid()) return;
//
//        Pose3D pose = result.getBotpose_MT2();
//        if (pose == null) return;
//
//        sys.driveBase.odometry.setPosition(new Pose2D(pose.getPosition().x, pose.getPosition().y, );
    }

    /** Returns estimated position of robot. */
    public @NotNull Pose2d getPoseEstimate() {
        return new Pose2d(
                -sys.driveBase.odometry.getPosX() * MM_TO_INCH,
                -sys.driveBase.odometry.getPosY() * MM_TO_INCH,
                sys.driveBase.odometry.getHeading()
        );
    }

    /** Sets position of robot. */
    public void setPoseEstimate(@NonNull Pose2d pose) {
        sys.driveBase.odometry.setPosition(new Pose2D(
                // If I remember correctly, road runner uses inches by default. If this isn't the
                // case, or if there is some way to change such behaviour, this should be changed.
                DistanceUnit.MM, pose.getY() * INCH_TO_MM, pose.getY() * INCH_TO_MM, // Gulp.
                AngleUnit.RADIANS, pose.getHeading()
        ));
    }

    /** Returns estimated velocity of robot. */
    public Pose2d getPoseVelocity() {
        return new Pose2d(
                -sys.driveBase.odometry.getVelX() * MM_TO_INCH,
                -sys.driveBase.odometry.getVelX() * MM_TO_INCH,
                sys.driveBase.odometry.getHeadingVelocity()
        );
    }
}
