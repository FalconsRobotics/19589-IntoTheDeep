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
import org.firstinspires.ftc.teamcode.utilities.Geometry;
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
    private Pose2d odometryOffset;
    private Pose2d correctedOdometryPos;

    // Used to convert to inches as roadrunner supposedly works best with those units.
    private final double INCH_TO_MM = 25.4;
    private final double MM_TO_INCH = 1 / INCH_TO_MM;

    /** @note This class does not manage any initialization procedures relating to the odometry
     *  module */
    public OdometryLimelightLocalizer(HardwareMap map) {
        sys = SubsystemsCollection.getInstance(null);
        vision = new VisionUtility(map);

        odometryOffset = new Pose2d(0.0, 0.0, 0.0);
        correctedOdometryPos = new Pose2d(0.0, 0.0, 0.0);

        update();
    }

    /** Will be ran every cycle. Using this with AutoDriveUtility will cause it to be ran on outside
     *  of the main thread, so never call this directly if doing so. */
    public void update() {
        // Remember that odometry.setPosition() doesn't seem to work for whatever reason, (likely
        // an implementation issue on my part) so a "correction" must be applied to our initial
        // odometry positions to apply an offset to start the robot on ourselves.
        // TODO: Test this.

        sys.driveBase.odometry.update();

        Pose2D oPos2D = sys.driveBase.odometry.getPosition();
        Geometry.Vector2D oPos = new Geometry.Vector2D(oPos2D.getX(DistanceUnit.INCH), oPos2D.getY(DistanceUnit.INCH));

        correctedOdometryPos = new Pose2d(
                oPos.x + odometryOffset.getX(),
                oPos.y + odometryOffset.getY(),
                oPos2D.getHeading(AngleUnit.RADIANS) + odometryOffset.getHeading()
        );

//        if((vision.getFieldPosition(sys.intake).getX(DistanceUnit.MM) != 0.0) && (vision.getFieldPosition(sys.intake).getY(DistanceUnit.MM) != 0.0)){
//            sys.driveBase.odometry.setPosition(vision.getFieldPosition(sys.intake));
//        }
    }

    /** Returns estimated position of robot. */
    public @NotNull Pose2d getPoseEstimate() {
        return correctedOdometryPos;
    }

    /** Sets position of robot. */
    public void setPoseEstimate(@NonNull Pose2d pose) {
        // New pose position assumed to be in inches.
        odometryOffset = new Pose2d(
                pose.getX() - correctedOdometryPos.getX(),
                pose.getY() - correctedOdometryPos.getY(),
                pose.getHeading() - correctedOdometryPos.getHeading()
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
