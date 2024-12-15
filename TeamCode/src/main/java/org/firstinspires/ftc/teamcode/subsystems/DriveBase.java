package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.arcrobotics.ftclib.controller.PIDController;
import com.arcrobotics.ftclib.drivebase.MecanumDrive;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import com.arcrobotics.ftclib.command.SubsystemBase;

import org.firstinspires.ftc.teamcode.external.GoBildaPinpointDriver;
import org.firstinspires.ftc.teamcode.utilities.ControlConstants;
import org.firstinspires.ftc.teamcode.utilities.DriveBaseMotors;


/** Manages all mechanisms associated with robot drive base. */
public class DriveBase extends SubsystemBase {
    /** Provides direct access to drive base motors. */
    public final DriveBaseMotors mDirect;

    /** Collection of all drive base motors. Managed by FTCLib. */
    private final MecanumDrive motors;

    /** Set powers applied to motors at the end of frame. Will reset back to zero by the start of
     *  each frame. Using this instead of calling FTCLib's drive robot functions directly will
     *  avoid any jittering on the drive base. */
    public Pose2d motorPowers; // Roadrunner
    /** Whether or not the drive base should be driven robot-centric (true) or field-centric (false). */
    public boolean driveRobotCentric;

    /** Whether or not robot driving should be handled directly by this object or not. Defaults to
     *  false. for handling drive powers on your own, set to true. */
    public boolean useExternalDriveCommands;

    /** Used to estimate robot position on field. */
    public final GoBildaPinpointDriver odometry;

    // For lockRotation().
    private final PIDController rotation;

    /** Initializes all members using 'map.' */
    public DriveBase(HardwareMap map) {
        mDirect = new DriveBaseMotors(
                new Motor(map, "DriveBase-FrontLeft", Motor.GoBILDA.RPM_435),
                new Motor(map, "DriveBase-FrontRight", Motor.GoBILDA.RPM_435),
                new Motor(map, "DriveBase-BackLeft", Motor.GoBILDA.RPM_435),
                new Motor(map, "DriveBase-BackRight", Motor.GoBILDA.RPM_435)
        );

        motors = new MecanumDrive(false, mDirect.frontLeft, mDirect.frontRight,
                mDirect.backLeft, mDirect.backRight);
        // For some odd reason sometimes this works and sometimes it doesn't.
        mDirect.frontLeft.setInverted(true);

        odometry = map.get(GoBildaPinpointDriver.class, "DriveBase-Odometry");

        odometry.setEncoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        odometry.setEncoderDirections(GoBildaPinpointDriver.EncoderDirection.REVERSED,
                GoBildaPinpointDriver.EncoderDirection.REVERSED);
        odometry.setOffsets(97.0, 72.5);

        odometry.resetPosAndIMU();

        motorPowers = new Pose2d(0.0, 0.0, 0.0);
        driveRobotCentric = true;
        useExternalDriveCommands = false;

        rotation = new PIDController(
                ControlConstants.DriveUtil.ROTATION_KP,
                ControlConstants.DriveUtil.ROTATION_KI,
                ControlConstants.DriveUtil.ROTATION_KD
        );
    }

    public void periodic() {
        odometry.update();
        if (useExternalDriveCommands) return;

        if (driveRobotCentric) {
            motors.driveRobotCentric(motorPowers.getY(), motorPowers.getX(), motorPowers.getHeading(),
                    true);
        } else {
            motors.driveFieldCentric(motorPowers.getY(), motorPowers.getX(), motorPowers.getHeading(),
                    odometry.getHeading() * (180 / Math.PI), true);
        }

        motorPowers = new Pose2d(0.0, 0.0, 0.0);
    }


    /**
     * Sets drive base motor behaviour to brake if `toggle` is set to true.
     */
    public void brake(boolean toggle) {
        if (toggle) {
            // This would be better with a for loop. TOO BAD!
            mDirect.frontLeft.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
            mDirect.frontRight.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
            mDirect.backLeft.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
            mDirect.backRight.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
            return;
        }

        mDirect.frontLeft.setZeroPowerBehavior(Motor.ZeroPowerBehavior.FLOAT);
        mDirect.frontRight.setZeroPowerBehavior(Motor.ZeroPowerBehavior.FLOAT);
        mDirect.backLeft.setZeroPowerBehavior(Motor.ZeroPowerBehavior.FLOAT);
        mDirect.backRight.setZeroPowerBehavior(Motor.ZeroPowerBehavior.FLOAT);
    }

    /** Attempts to lock rotation to a certain heading (radians). Based on DriveUtil rotation
     *  constants. Must be called every frame to have any affect. */
    public void lockRotation(double heading) {
        final double FULL_ROTATION = Math.PI * 2;

        rotation.setSetPoint(heading % FULL_ROTATION);
        double power = rotation.calculate(odometry.getHeading() % FULL_ROTATION);

        // Positive headings start counter-clockwise.
        motorPowers = new Pose2d(motorPowers.getX(), motorPowers.getY(), -power);
    }
}