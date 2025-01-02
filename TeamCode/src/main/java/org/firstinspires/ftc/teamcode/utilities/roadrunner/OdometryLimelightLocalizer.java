package org.firstinspires.ftc.teamcode.utilities.roadrunner;

import androidx.annotation.NonNull;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.localization.Localizer;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.teamcode.subsystems.DriveBase;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.utilities.SubsystemsCollection;
import org.firstinspires.ftc.teamcode.utilities.vision.VisionUtility;
import org.jetbrains.annotations.NotNull;

import java.util.List;


/** Formats odometry data to be used with Road Runner, attempts to correct position using
 *  limelight. */
public class OdometryLimelightLocalizer implements Localizer {
    // For getting odometry.
    private final SubsystemsCollection sys;
    private final VisionUtility vision;

    // Odometry computer does not seem to actually set position when calling setPosition, setting
    // positions using this localizer will affect this variable instead.
    private Pose2D odometryOffset;
    private Pose2D correctedOdometryPos;

    // Used to convert to inches as roadrunner supposedly works best with those units.
    private final double INCH_TO_MM = 25.4;
    private final double MM_TO_INCH = 1 / INCH_TO_MM;

    /** @note This class does not manage any initialization procedures relating to the odometry
     *  module */
    public OdometryLimelightLocalizer(HardwareMap map) {
        sys = SubsystemsCollection.getInstance(null);
        vision = new VisionUtility(map);
        setPoseEstimate(new Pose2d(0.0, 0.0, 0.0));
    }

    /** Will be ran every cycle. Using this with AutoDriveUtility will cause it to be ran on outside
     *  of the main thread, so never call this directly if doing so. */
    public void update() {
        sys.driveBase.odometry.update();
        Pose2D odometryPos = sys.driveBase.odometry.getPosition();

        correctedOdometryPos = new Pose2D(
                DistanceUnit.INCH,
                -odometryPos.getX(DistanceUnit.INCH) + odometryOffset.getX(DistanceUnit.INCH),
                -odometryPos.getY(DistanceUnit.INCH) + odometryOffset.getY(DistanceUnit.INCH),
                AngleUnit.RADIANS,
                odometryPos.getHeading(AngleUnit.RADIANS) + odometryOffset.getHeading(AngleUnit.RADIANS)
        );

//        if((vision.getFieldPosition(sys.intake).getX(DistanceUnit.MM) != 0.0) && (vision.getFieldPosition(sys.intake).getY(DistanceUnit.MM) != 0.0)){
//            sys.driveBase.odometry.setPosition(vision.getFieldPosition(sys.intake));
//        }
    }

    /** Returns estimated position of robot. */
    public @NotNull Pose2d getPoseEstimate() {
        return new Pose2d(
                correctedOdometryPos.getX(DistanceUnit.INCH),
                correctedOdometryPos.getY(DistanceUnit.INCH),
                correctedOdometryPos.getHeading(AngleUnit.RADIANS)
        );
    }

    /** Sets position of robot. */
    public void setPoseEstimate(@NonNull Pose2d pose) {
        odometryOffset = new Pose2D(
                // If I remember correctly, road runner uses inches by default. If this isn't the
                // case, or if there is some way to change such behaviour, this should be changed.
                DistanceUnit.INCH, pose.getY(), pose.getY(), // Gulp.
                AngleUnit.RADIANS, pose.getHeading()
        );
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
