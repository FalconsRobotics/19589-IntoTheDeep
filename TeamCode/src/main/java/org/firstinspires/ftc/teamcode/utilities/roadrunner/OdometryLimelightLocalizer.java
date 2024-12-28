package org.firstinspires.ftc.teamcode.utilities.roadrunner;

import androidx.annotation.NonNull;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.localization.Localizer;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.subsystems.DriveBase;
import org.firstinspires.ftc.teamcode.utilities.SubsystemsCollection;
import org.jetbrains.annotations.NotNull;


/** Formats odometry data to be used with Road Runner, attempts to correct position using
 *  limelight. */
public class OdometryLimelightLocalizer implements Localizer {
    // For getting odometry.
    private final DriveBase driveBase;

    // Used to convert to inches as roadrunner supposedly works best with those units.
    private final double INCH_TO_MM = 25.4;
    private final double MM_TO_INCH = 1 / INCH_TO_MM;

    /** @note This class does not manage any initialization procedures relating to the odometry
     *  module */
    public OdometryLimelightLocalizer() {
        driveBase = SubsystemsCollection.getInstance(null).driveBase;

        driveBase.odometry.resetPosAndIMU();
        driveBase.odometry.setPosition(new Pose2D(DistanceUnit.MM, 0.0, 0.0, AngleUnit.RADIANS, 0.0));
    }

    /** Will be ran every cycle. Using this with AutoDriveUtility will cause it to be ran on outside
     *  of the main thread, so never call this directly if doing so. */
    public void update() {
        driveBase.odometry.update();
    }

    /** Returns estimated position of robot. */
    public @NotNull Pose2d getPoseEstimate() {
        return new Pose2d(
                -driveBase.odometry.getPosX() * MM_TO_INCH,
                -driveBase.odometry.getPosY() * MM_TO_INCH,
                driveBase.odometry.getHeading()
        );
    }

    /** Sets position of robot. */
    public void setPoseEstimate(@NonNull Pose2d pose) {
        driveBase.odometry.setPosition(new Pose2D(
                // If I remember correctly, road runner uses inches by default. If this isn't the
                // case, or if there is some way to change such behaviour, this should be changed.
                DistanceUnit.MM, pose.getY() * INCH_TO_MM, pose.getY() * INCH_TO_MM, // Gulp.
                AngleUnit.RADIANS, pose.getHeading()
        ));
    }

    /** Returns estimated velocity of robot. */
    public Pose2d getPoseVelocity() {
        return new Pose2d(
                -driveBase.odometry.getVelX() * MM_TO_INCH,
                -driveBase.odometry.getVelX() * MM_TO_INCH,
                driveBase.odometry.getHeadingVelocity()
        );
    }
}
