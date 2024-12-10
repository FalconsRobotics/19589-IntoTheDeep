package org.firstinspires.ftc.teamcode.subsystems;

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
    public final MecanumDrive motors;

    /** Used to estimate robot position on field. */
    public final GoBildaPinpointDriver odometry;

    /** For lockRotation() */
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

        rotation = new PIDController(
                ControlConstants.DriveUtil.ROTATION_KP,
                ControlConstants.DriveUtil.ROTATION_KI,
                ControlConstants.DriveUtil.ROTATION_KD
        );
    }

    public void periodic() {
        odometry.update();
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

    /** Attempts to lock rotation to a certain heading depending, based on DriveUtil rotation
     *  constants. Must be called every frame to have any affect. */
    public void lockRotation(double heading) {
        double power = rotation.calculate(odometry.getHeading());

        // Positive headings start counter-clockwise.
        mDirect.frontLeft.set(-power);
        mDirect.backLeft.set(-power);
        mDirect.frontRight.set(power);
        mDirect.backRight.set(power);
    }
}