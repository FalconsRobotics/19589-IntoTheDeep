package org.firstinspires.ftc.teamcode.utilities;

import com.acmerobotics.roadrunner.control.PIDCoefficients;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.arcrobotics.ftclib.controller.PIDController;

import org.firstinspires.ftc.teamcode.subsystems.DriveBase;
import org.firstinspires.ftc.teamcode.utilities.roadrunner.OdometryPodLocalizer;

public class CustomAutoDriveUtil {
    private final DriveBase driveBase;
    private final OdometryPodLocalizer localizer;

    private final PIDController forward, strafe, rotation;
    private final double maxPower;


    public CustomAutoDriveUtil(DriveBase driveBase, double maxPower) {
        this.driveBase = driveBase;
        localizer = new OdometryPodLocalizer(driveBase.odometry);

        forward = new PIDController(
                ControlConstants.DriveUtil.TRANSLATION_KP,
                ControlConstants.DriveUtil.TRANSLATION_KI,
                ControlConstants.DriveUtil.TRANSLATION_KD
        );

        strafe = new PIDController(
                ControlConstants.DriveUtil.TRANSLATION_KP,
                ControlConstants.DriveUtil.TRANSLATION_KI,
                ControlConstants.DriveUtil.TRANSLATION_KD
        );

        rotation = new PIDController(
                ControlConstants.DriveUtil.ROTATION_KP,
                ControlConstants.DriveUtil.ROTATION_KI,
                ControlConstants.DriveUtil.ROTATION_KD
        );

        this.maxPower = maxPower;
    }

    public void periodic(boolean translate, boolean rotate) {
        Pose2d pose = localizer.getPoseEstimate();
        double forwardPwr = 0.0, strafePwr = 0.0, rotationPwr = 0.0;

        if (translate) {
            forwardPwr = Clamp.clamp(forward.calculate(pose.getX()), -maxPower, maxPower);
            strafePwr = Clamp.clamp(strafe.calculate(pose.getY() * ControlConstants.DriveUtil.STRAFE_MULTIPLIER), -maxPower, -maxPower);
        }

        if (rotate) {
            rotationPwr = Clamp.clamp(rotation.calculate(pose.getHeading()), -maxPower, maxPower);
        }

        driveBase.motors.driveFieldCentric(strafePwr, forwardPwr, rotationPwr, localizer.getHeadingDegrees(), true);
    }

    public void goTo(Pose2d target) {
        forward.setSetPoint(target.getX());
        strafe.setSetPoint(target.getX());
        rotation.setSetPoint(target.getHeading());
    }
}
