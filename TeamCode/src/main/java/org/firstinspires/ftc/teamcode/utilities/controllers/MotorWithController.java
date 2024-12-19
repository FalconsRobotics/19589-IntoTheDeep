package org.firstinspires.ftc.teamcode.utilities.controllers;

import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.utilities.PowerRegulator;

public abstract class MotorWithController {
    /** Motor accessed through controller. */
    public final Motor motor;

    /** Accounts for voltage differences. */
    public final PowerRegulator regulator;

    // Protected to ensure this class cannot be created as itself.
    protected MotorWithController(HardwareMap map, String name, Motor.GoBILDA type) {
        motor = new Motor(map, name, type);
        regulator = PowerRegulator.getInstance(map);
    }

    /** sets motor power with calculation. */
    public void setMotorPower() {
        motor.set(calculateMotorPower() * regulator.getPowerMultiplier());
    }

    /** Implemented calculation to set motor power with*/
    public abstract double calculateMotorPower();
    /** Sets new target for motor to go to, in ticks. */
    public abstract void setTarget(int target);
    /** Returns whether or not motor is at it's desired target. */
    public abstract boolean atTarget();
}
