package org.firstinspires.ftc.teamcode.utilities;

import com.arcrobotics.ftclib.hardware.motors.Motor;

/** Provides direct access to supplied motors passed inside of its constructor. Used mainly so aid
 *  in passing motors to both FTCLib and Road Runner. */
public class DriveBaseMotors {
    public final Motor frontLeft, frontRight, backLeft, backRight;

    public DriveBaseMotors(Motor frontLeft, Motor frontRight, Motor backLeft, Motor backRight) {
        this.frontLeft = frontLeft;
        this.frontRight = frontRight;
        this.backLeft = backLeft;
        this.backRight = backRight;
    }
}
