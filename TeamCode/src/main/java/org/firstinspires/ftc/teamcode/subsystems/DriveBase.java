package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.ext.GoBildaPinpointDriver;

/** Manages all mechanisms associated with robot drive base. */
public class DriveBase {

    /** Indexes referencing wheel positions on drive base. */
    private static class WheelPos {
        // Wanted an enum  class, however did not want to use .ordinal() to
        // refer to these every time. Ugly? Maybe. TOO BAD!
        final static int FRONT_DRIVER = 0;
        final static int BACK_DRIVER = 1;
        final static int FRONT_PASSENGER = 2;
        final static int BACK_PASSENGER = 3;
        final static int LAST = BACK_PASSENGER + 1;
    }

    /** As strafing requires more power than moving forward, inputs will be scaled to this degree to
     *  help maintain a uniform amount of movement in all directions*/
    private final double STRAFE_CORRECTION = 1.1;

    /** Array containing all wheel motors used by drive base. Positions referenced by members within
     *  the \ref WheelPos class. */
    private final DcMotor[] wheels;

    // TODO: Documentation
    private final GoBildaPinpointDriver odometry;

    /** Initializes drive base motors and odometry, including any of their associated flags. */
    public DriveBase(HardwareMap map) {
        // Wheel motors setup:
        wheels = new DcMotor[WheelPos.LAST];
        for (int i = 0; i < wheels.length; i++) {
            wheels[i] = map.get(DcMotor.class, "DriveBase-Wheel" + i);
            wheels[i].setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }

        // Simplifies all future calculations applied to all wheel motors.
        wheels[WheelPos.BACK_DRIVER].setDirection(DcMotor.Direction.REVERSE);
        wheels[WheelPos.BACK_PASSENGER].setDirection(DcMotorSimple.Direction.REVERSE);

        // Odometry computer setup:
        odometry = map.get(GoBildaPinpointDriver.class, "DriveBase-Odemetry");
        odometry.setEncoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        odometry.setEncoderDirections(GoBildaPinpointDriver.EncoderDirection.FORWARD, GoBildaPinpointDriver.EncoderDirection.FORWARD);
        odometry.setOffsets(-100, 80); // TODO

        odometry.resetPosAndIMU();
    }

    /** Miscellanous procedures that need to be ran every iteration */
    public void update() {
        odometry.update();
    }

    /** Cleans up resources used by drive base.  */
    public void finalize() {
        // Do nothing.
    }

    /** Sets the drive base's positinal and rotational velocity relative to the last known position
     *  of itself. */
    public void setVelocity(double forward, double strafe, double rotation) {
        // Allows for uniform movement forward and strafing.
        strafe *= STRAFE_CORRECTION;

        wheels[WheelPos.FRONT_DRIVER].setPower(forward + strafe + rotation);
        wheels[WheelPos.FRONT_PASSENGER].setPower(forward - strafe + rotation);
        wheels[WheelPos.BACK_DRIVER].setPower(forward - strafe - rotation);
        wheels[WheelPos.BACK_PASSENGER].setPower(forward + strafe - rotation);
    }

    /** Sets the drive base's positional and rotational velocity relative to its starting position
     *  on the field. */
    public void setVelocityFieldCentric(double forward, double strafe, double rotation) {
        double heading = odometry.getHeading();

        // Avoids multiple executions costly procedures.
        double sinHeading = Math.sin(heading);
        double cosHeading = Math.cos(heading);

        double forwardRotated = strafe * sinHeading + forward * cosHeading;
        double strafeRotated = strafe * cosHeading - forward * sinHeading * STRAFE_CORRECTION;

        // TODO: Name is not ideal.
        // Total power applied to motors must be clamped to one to ensure robot moves in desired
        // direction.
        double denominator = Math.max(Math.abs(forwardRotated) + Math.abs(strafeRotated) + Math.abs(rotation), 1);

        wheels[WheelPos.FRONT_DRIVER].setPower((forward + strafe + rotation) / denominator);
        wheels[WheelPos.FRONT_PASSENGER].setPower((forward - strafe + rotation) / denominator);
        wheels[WheelPos.BACK_DRIVER].setPower((forward - strafe - rotation) / denominator);
        wheels[WheelPos.BACK_PASSENGER].setPower((forward + strafe - rotation) / denominator);
    }
}
