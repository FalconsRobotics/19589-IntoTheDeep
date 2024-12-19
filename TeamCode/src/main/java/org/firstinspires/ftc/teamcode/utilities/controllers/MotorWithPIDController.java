package org.firstinspires.ftc.teamcode.utilities.controllers;

import com.arcrobotics.ftclib.controller.PIDController;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.utilities.Clamp;

public class MotorWithPIDController extends MotorWithController {
    /** Controller used to set the power to associated motor */
    public final PIDController controller;
    /** Maximum power able to be applied to motor.  */
    public final double maxPower;

    /** Initializes a motor with a PID controller given the associated gains. */
    public MotorWithPIDController(HardwareMap map, String name, Motor.GoBILDA type, PIDController controller, int tolerance, double maxPower) {
        super(map, name, type);
        motor.setRunMode(Motor.RunMode.RawPower);
        motor.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);

        this.controller = controller;
        this.controller.setTolerance(tolerance);
        this.controller.setSetPoint(0);

        this.maxPower = maxPower;
    }

    public void setTarget(int target) {
        controller.setSetPoint(target);
    }

    public boolean atTarget() {
        return controller.atSetPoint();
    }

    // For MotorWithPIDFController to use.
    public double calculateMotorPower() {
        return Clamp.clamp(controller.calculate(motor.getCurrentPosition()), -maxPower, maxPower);
    }
}
