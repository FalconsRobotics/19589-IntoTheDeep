package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.controller.PIDFController;
import com.arcrobotics.ftclib.hardware.motors.Motor;

// TODO: ALL OF THIS NEEDS CLEANED UP AFTER 1ST COMPETITION

public class MotorController {
    private final Motor motor;
    private final PIDFController controller;
    private final double maxPower;

    public MotorController(Motor motor, double kp, double ki, double kd, double kf, int tolerance, double maxPower) {
        this.motor = motor;
        motor.setRunMode(Motor.RunMode.RawPower);
        motor.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);

        controller = new PIDFController(kp, ki, kd, kf);
        controller.setTolerance(tolerance);
        setTargetPosition(0);

        this.maxPower = maxPower;
    }

    public void setTargetPosition(int position) {
        // motor.setTargetPosition(position);
        controller.setSetPoint(position);
    }

    public void setMotorPower() {
            motor.set(Math.min(
                    maxPower,
                    controller.calculate(motor.getCurrentPosition()) * maxPower)
            );
    }

    public int getTargetPosition() {
        return (int) controller.getSetPoint();
    }

    public boolean atTarget() { return controller.atSetPoint(); }
}
