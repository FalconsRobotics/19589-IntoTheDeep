package org.firstinspires.ftc.teamcode.utilities.roadrunner;

import androidx.annotation.NonNull;

import com.acmerobotics.roadrunner.drive.MecanumDrive;

import org.firstinspires.ftc.teamcode.utilities.DriveBaseMotors;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/** Mecanum drive implementation to be used with RoadRunner. */
public class RRMecanumDriveImpl extends MecanumDrive {
    private final DriveBaseMotors mDirect;

    public RRMecanumDriveImpl(DriveBaseMotors motors, OdometryPodLocalizer localizer,
                              double kV, double kA, double kStatic,
                              double trackWidth, double wheelBase, double lateralMultiplier) {
        super(kV, kA, kStatic, trackWidth, wheelBase, lateralMultiplier);
        setLocalizer(localizer);

        mDirect = motors;
    }

    @NonNull
    public List<Double> getWheelPositions() {
        // Must be returned in the same order as seen in `setMotorPowers().`
//        return Arrays.asList(
//                mDirect.frontLeft.get(),
//                mDirect.backLeft.get(),
//                mDirect.backRight.get(),
//                mDirect.frontRight.get()
//        );

        // I don't understand the need to get wheel positions. Don't we already have a localizer?
        return Arrays.asList(); // Yes this is intended.
    }

    public void setMotorPowers(double v, double v1, double v2, double v3) {
            mDirect.frontLeft.set(v);
            mDirect.backLeft.set(v1);
            mDirect.backRight.set(v2);
            mDirect.frontRight.set(v3);
    }

    protected double getRawExternalHeading() {
        // This is bad, and assumes you are using OdometryPodLocalizer. TOO BAD!
        return getLocalizer().getPoseEstimate().getHeading();
    }
}
