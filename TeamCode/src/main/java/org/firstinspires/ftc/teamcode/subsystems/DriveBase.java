package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.drivebase.MecanumDrive;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import com.arcrobotics.ftclib.command.SubsystemBase;

import org.firstinspires.ftc.teamcode.external.GoBildaPinpointDriver;
import org.firstinspires.ftc.teamcode.utilities.DriveBaseMotors;


/** Manages all mechanisms associated with robot drive base. */
public class DriveBase extends SubsystemBase {
    /** Provides direct access to drive base motors. */
    public final DriveBaseMotors mDirect;

    /** Collection of all drive base motors. Managed by FTCLib. */
    public final MecanumDrive motors;

    /** Used to estimate robot position on field. */
    public final GoBildaPinpointDriver odometry;
//    public final Limelight3A limelight;

    /**
     * Initializes all members using 'map.'
     */
    public DriveBase(HardwareMap map) {
        mDirect = new DriveBaseMotors(
                new Motor(map, "DriveBase-FrontLeft", Motor.GoBILDA.RPM_435),
                new Motor(map, "DriveBase-FrontRight", Motor.GoBILDA.RPM_435),
                new Motor(map, "DriveBase-BackLeft", Motor.GoBILDA.RPM_435),
                new Motor(map, "DriveBase-BackRight", Motor.GoBILDA.RPM_435)
        );

        motors = new MecanumDrive(false, mDirect.frontLeft, mDirect.frontRight,
                mDirect.backLeft, mDirect.backRight);
        // For some odd reason: Sometimes this works, sometimes it doesn't.
        mDirect.frontLeft.setInverted(true);

        odometry = map.get(GoBildaPinpointDriver.class, "DriveBase-Odometry");
        odometry.setEncoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        odometry.setEncoderDirections(GoBildaPinpointDriver.EncoderDirection.REVERSED,
                GoBildaPinpointDriver.EncoderDirection.FORWARD);
        odometry.setOffsets(73, -100);

        odometry.resetPosAndIMU();

//        limelight = map.get(Limelight3A.class, "Limelight");
//        limelight.setPollRateHz(30);
//        limelight.start();
    }

    public void periodic() {
        odometry.update();
    }

    /**
     * Brakes drive base if `toggle` is set to true.
     *
     * @note This must be called after any other driving functions to have any effect.
     */
    public void brake(boolean toggle) {
        if (toggle) {
            // This would be better with a for loop. TOO BAD!
            mDirect.frontLeft.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
            mDirect.frontRight.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
            mDirect.backLeft.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
            mDirect.backRight.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        } else {
            mDirect.frontLeft.setZeroPowerBehavior(Motor.ZeroPowerBehavior.FLOAT);
            mDirect.frontRight.setZeroPowerBehavior(Motor.ZeroPowerBehavior.FLOAT);
            mDirect.backLeft.setZeroPowerBehavior(Motor.ZeroPowerBehavior.FLOAT);
            mDirect.backRight.setZeroPowerBehavior(Motor.ZeroPowerBehavior.FLOAT);
        }
    }
}