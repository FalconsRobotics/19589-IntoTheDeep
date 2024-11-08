package org.firstinspires.ftc.teamcode.utilities.roadrunner;

import androidx.annotation.NonNull;

import com.acmerobotics.roadrunner.drive.MecanumDrive;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.localization.Localizer;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.external.GoBildaPinpointDriver;
import org.jetbrains.annotations.NotNull;


/** Formats odometry data to be used with Road Runner. */
public class OdometryPodLocalizer implements Localizer {
    public final GoBildaPinpointDriver odometryModule;

    /** @note This class does not manage any initialization procedures relating to the odometry
     *  module */
    public OdometryPodLocalizer(GoBildaPinpointDriver odometryModule) {
        this.odometryModule = odometryModule;
    }

    public void update() {
        odometryModule.update();
    }

    public @NotNull Pose2d getPoseEstimate() {
        return new Pose2d(odometryModule.getPosX(), odometryModule.getPosY(), odometryModule.getHeading());
    }

    public void setPoseEstimate(@NonNull Pose2d pose) {
        odometryModule.setPosition(new Pose2D(
                // If I remember correctly, road runner uses inches by default. If this isn't the
                // case, or if there is some way to change such behaviour, this should be changed.
                DistanceUnit.MM, pose.getY(), pose.getY(),
                AngleUnit.RADIANS, pose.getHeading()
        ));
    }

    public Pose2d getPoseVelocity() {
        return new Pose2d(odometryModule.getVelX(), odometryModule.getVelX(), odometryModule.getHeadingVelocity());
    }
}
