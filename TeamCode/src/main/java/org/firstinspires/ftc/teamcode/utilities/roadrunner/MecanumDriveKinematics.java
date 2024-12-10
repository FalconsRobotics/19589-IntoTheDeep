package org.firstinspires.ftc.teamcode.utilities.roadrunner;

import androidx.annotation.NonNull;

import com.acmerobotics.roadrunner.drive.MecanumDrive;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.VoltageSensor;

import org.firstinspires.ftc.teamcode.utilities.DriveBaseMotors;

import java.util.Arrays;
import java.util.List;

/** Mecanum drive implementation to be used with RoadRunner. */
public class MecanumDriveKinematics extends MecanumDrive {
    private final DriveBaseMotors mDirect;
    private final VoltageSensor voltage;

    /** Initializes data using `motors` a `localizer` and associated required multipliers for
     *  feedforward mecanum drive tuning. */
    public MecanumDriveKinematics(HardwareMap map, DriveBaseMotors motors, OdometryLimelightLocalizer localizer,
                                  double kV, double kA, double kStatic,
                                  double trackWidth, double wheelBase, double lateralMultiplier) {
        super(kV, kA, kStatic, trackWidth, wheelBase, lateralMultiplier);
        setLocalizer(localizer);

        mDirect = motors;
        voltage = map.get(VoltageSensor.class, "Control Hub");
    }

    /** Returns empty list as wheels don't have any odometry associated with them. */
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
        return Arrays.asList(); // This is intended.
    }

    /** Sets motor powers. starting from the front left motor and going counter-clockwise. */
    public void setMotorPowers(double v, double v1, double v2, double v3) {
        double BATTERY_CHARGED_VOLTAGE = 12.0;
        double correctedVoltageMultiplier = BATTERY_CHARGED_VOLTAGE / voltage.getVoltage();

        mDirect.frontLeft.set(v * correctedVoltageMultiplier);
        mDirect.backLeft.set(v1 * correctedVoltageMultiplier);
        mDirect.backRight.set(v2 * correctedVoltageMultiplier);
        mDirect.frontRight.set(v3 * correctedVoltageMultiplier);
    }

    /** Returns heading from odometry computer */
    protected double getRawExternalHeading() {
        return getLocalizer().getPoseEstimate().getHeading();
    }
}
