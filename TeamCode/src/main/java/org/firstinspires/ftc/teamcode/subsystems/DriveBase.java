package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;

/** Manages all mechanisms associated with robot drive base. */
public class DriveBase {

    public enum WheelPosition {
        FRONT_LEFT,
        FRONT_RIGHT,
        BACK_RIGHT,
        BACK_LEFT,
        LAST // Used to get array size.
    }

    private final DcMotor[] wheel;

    /** Initializes drive base motors and any of their associated flags. */
    public DriveBase() {
        wheel = new DcMotor[WheelPosition.LAST.ordinal()];
    }

    /** Cleans up resources used by drive base.  */
    public void finalize() {

    }
}
