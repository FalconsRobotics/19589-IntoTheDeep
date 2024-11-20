package org.firstinspires.ftc.teamcode.utilities.controllers;

import com.arcrobotics.ftclib.controller.PIDFController;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.hardware.HardwareMap;

// TODO: ALL OF THIS NEEDS CLEANED UP AFTER 1ST COMPETITION

public class MotorWithPIDFController extends MotorWithController {
    public final PIDFController controller;
    public final double maxPower;

    /** Initializes a motor with a PID controller given the associated gains. */
    public MotorWithPIDFController(HardwareMap map, String name, double kp, double ki, double kd, double kf, int tolerance, double maxPower) {
        super(map, name);
        motor.setRunMode(Motor.RunMode.RawPower);
        motor.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);

        controller = new PIDFController(kp, ki, kd, kf);
        controller.setTolerance(tolerance);
        controller.setSetPoint(0);

        this.maxPower = maxPower;
    }

    public void setMotorPower() {
            motor.set(Math.min(
                    maxPower,
                    controller.calculate(motor.getCurrentPosition()) * maxPower)
            );
    }

    public void setTarget(int target) {
        controller.setSetPoint(target);
    }

    public boolean atTarget() {
        return controller.atSetPoint();
    }
}
