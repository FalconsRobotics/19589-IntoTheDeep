package org.firstinspires.ftc.teamcode.subsystems;

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
        final static int BACK_DRIVER = 1;
        final static int FRONT_PASSENGER = 2;
        final static int BACK_PASSENGER = 3;
        final static int LAST = BACK_PASSENGER + 1;
    }

    private final DcMotor[] wheels;

    /** Initializes drive base motors and any of their associated flags. */
    public DriveBase(HardwareMap map) {
        wheels = new DcMotor[WheelPos.LAST];
        for (int i = 0; i < wheels.length; i++) {
            wheels[i] = map.dcMotor.get("DriveBase-Wheel" + i);
            wheels[i].setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
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
        double angle = Math.atan2(strafe, forward);
        double power = Math.hypot(forward, strafe);

        final double piDiv4 = Math.PI / 4; // 45 degrees in radians.
        // Avoids costly re-calculations;
        double sinCalc = Math.sin(angle - piDiv4);
        double cosCalc = Math.cos(angle - piDiv4);

        double max = Math.max(Math.abs(sinCalc), Math.abs(cosCalc));
        sinCalc /= max;
        cosCalc /= max;

        wheels[WheelPos.FRONT_DRIVER].setPower((power * cosCalc + rotation) * multiplier);
        wheels[WheelPos.FRONT_PASSENGER].setPower((power * sinCalc + rotation) * multiplier);
        wheels[WheelPos.BACK_DRIVER].setPower((power * sinCalc - rotation) * multiplier);
        wheels[WheelPos.BACK_PASSENGER].setPower((power * cosCalc - rotation) * multiplier);
    }
}
