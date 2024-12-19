package org.firstinspires.ftc.teamcode.utilities.controllers;

import com.arcrobotics.ftclib.controller.PIDController;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.utilities.Clamp;

/** For some reason FTCLib's PIDF Controller wasn't working for me, hence why this exists. Kind
 *  of a hack. TOO BAD! */
public class MotorWithPIDFController extends MotorWithPIDController {
    /** Feedforward constant - Added force applied to final PID Calculation, Accounts for sign. */
    public final double kF;

    public MotorWithPIDFController(HardwareMap map, String name, Motor.GoBILDA type, PIDController controller, double kF, int tolerance, double maxPower) {
        super(map, name, type, controller, tolerance, maxPower);
        this.kF = kF;
    }


    public double calculateMotorPower() {
        double calculation = super.calculateMotorPower();

        calculation = (Clamp.clamp(
                calculation + (kF * Math.signum(calculation)),
                -maxPower, maxPower
        ));

        return calculation;
    }
}
