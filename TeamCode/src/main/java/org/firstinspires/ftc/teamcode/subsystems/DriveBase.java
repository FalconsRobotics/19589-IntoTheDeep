package org.firstinspires.ftc.teamcode.subsystems;

<<<<<<< HEAD
import com.arcrobotics.ftclib.drivebase.MecanumDrive;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import com.arcrobotics.ftclib.command.SubsystemBase;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.ext.GoBildaPinpointDriver;


/** Manages all mechanisms associated with robot drive base. */
public class DriveBase extends SubsystemBase {
    // Allows for direct access to the motors used by MecanumDrive. Used for braking.
    private static class MotorsDirectAccess {
        public final Motor frontLeft, frontRight, backLeft, backRight;
        public MotorsDirectAccess(Motor frontLeft, Motor frontRight, Motor backLeft, Motor backRight) {
            this.frontLeft = frontLeft;
            this.frontRight = frontRight;
            this.backLeft = backLeft;
            this.backRight = backRight;
        }
    }
    private final MotorsDirectAccess mDirect;

    /** Collection of all drive base motors. Managed by FTCLib. */
    public final MecanumDrive motors;
    /** Used to estimate robot position on field. */
    public final GoBildaPinpointDriver odometry;
//    public final Limelight3A limelight;

    /** Initializes all members using 'map.' */
    public DriveBase(HardwareMap map) {
        mDirect = new MotorsDirectAccess(
                new Motor(map, "DriveBase-FrontLeft", Motor.GoBILDA.RPM_435),
                new Motor(map, "DriveBase-FrontRight", Motor.GoBILDA.RPM_435),
                new Motor(map, "DriveBase-BackLeft", Motor.GoBILDA.RPM_435),
                new Motor(map, "DriveBase-BackRight", Motor.GoBILDA.RPM_435)
        );

        motors = new MecanumDrive(false, mDirect.frontLeft, mDirect.frontRight,
                mDirect.backLeft, mDirect.backRight);
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

    /** Brakes drive base if `toggle` is set to true.
     *  @note This must be called after any other driving functions to have any effect. */
    public void brake(boolean toggle) {
        if (toggle) {
            // This would be better with a for loop. TOO BAD!
            mDirect.frontLeft.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
            mDirect.frontRight.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
            mDirect.backLeft.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
            mDirect.backRight.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);

            motors.driveWithMotorPowers(0.0, 0.0, 0.0, 0.0);
        } else {
            mDirect.frontLeft.setZeroPowerBehavior(Motor.ZeroPowerBehavior.FLOAT);
            mDirect.frontRight.setZeroPowerBehavior(Motor.ZeroPowerBehavior.FLOAT);
            mDirect.backLeft.setZeroPowerBehavior(Motor.ZeroPowerBehavior.FLOAT);
            mDirect.backRight.setZeroPowerBehavior(Motor.ZeroPowerBehavior.FLOAT);
        }
=======
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;

/** Manages all mechanisms associated with robot drive base. */
public class DriveBase {

    /** Indexes referencing wheel positions on drive base. */
    private static class WheelPos {
        // Wanted an enum  class, however did not want to use .ordinal() to
        // refer to these every time. Ugly? Maybe. TOO BAD!
        final static int FRONT_DRIVER = 0;
        final static int FRONT_PASSENGER = FRONT_DRIVER + 1;
        final static int BACK_DRIVER = FRONT_PASSENGER + 1;
        final static int BACK_PASSENGER = BACK_DRIVER + 1;
        final static int LAST = BACK_PASSENGER + 1;
    }

    private final DcMotor[] wheels;

    /** Initializes drive base motors and any of their associated flags. */
    public DriveBase(HardwareMap map) {
        wheels = new DcMotor[WheelPos.LAST];
        for (int i = 0; i < wheels.length; i++) {
            wheels[i] = map.dcMotor.get("DriveBase-Wheel" + i);
            wheels[i].setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        }

        // Simplifies all future calculations applied to all wheel motors
        wheels[WheelPos.BACK_DRIVER].setDirection(DcMotor.Direction.REVERSE);
        wheels[WheelPos.BACK_PASSENGER].setDirection(DcMotorSimple.Direction.REVERSE);
    }

    /** Cleans up resources used by drive base.  */
    public void finalize() {
        // Do nothing.
    }

    // TODO: Documentation

    public void setVelocity(double forward, double strafe, double rotation, double multiplier) {
        double angle = Math.atan2(forward, strafe);
        double power = Math.hypot(strafe, forward);

        final double piDiv4 = Math.PI / 4; // 45 degrees in radians.
        // Avoids costly re-calculations;
        double sinCalc = Math.sin(angle - piDiv4);
        double cosCalc = Math.cos(angle - piDiv4);

        double max = Math.max(Math.abs(sinCalc), Math.abs(cosCalc));
        sinCalc /= max;
        cosCalc /= max;

        wheels[WheelPos.FRONT_DRIVER].setPower((power * cosCalc + rotation) * multiplier);
        wheels[WheelPos.FRONT_PASSENGER].setPower((power * sinCalc - rotation) * multiplier);
        wheels[WheelPos.BACK_DRIVER].setPower((power * sinCalc + rotation) * multiplier);
        wheels[WheelPos.BACK_PASSENGER].setPower((power * cosCalc - rotation) * multiplier);
>>>>>>> master
    }
}
