package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.ext.GoBilaPinpointDriver;

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
        double angle = Math.atan2(strafe, forward);
        double power = Math.hypot(forward, strafe);

        final double piDiv4 = Math.PI / 4; // 45 degrees in radians.
        // Avoids costly re-calculations;
        double sinCalc = Math.sin(angle - piDiv4);
        double cosCalc = Math.cos(angle - piDiv4);

        double max = Math.max(Math.abs(sinCalc), Math.abs(cosCalc));
        sinCalc /= max;
        cosCalc /= max;

        wheels[WheelPos.FRONT_DRIVER].setPower(power * cosCalc + rotation);
        wheels[WheelPos.FRONT_PASSENGER].setPower(power * sinCalc + rotation);
        wheels[WheelPos.BACK_DRIVER].setPower(power * sinCalc - rotation);
        wheels[WheelPos.BACK_PASSENGER].setPower(power * cosCalc - rotation);
    }

    /** Sets the drive base's positional and rotational velocity relative to its starting position
     *  on the field. */
    public void setVelocityFieldCentric(double foward, double strafe, double rotate) {
        // May not be the most effiecent use of sin and cos. Counter-point: Simple to implement--
        // TOO BAD!
        double relativeRotation = odo.getPosition().heading;
        relativeRotation *= (Math.pi / 180) // Converts from degrees to radians.
        setVelocity(foward * Math.sin(relativeRotation), strafe * Math.cos(relativeRotation), rotate);
    }
}
