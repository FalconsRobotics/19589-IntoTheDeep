package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.controller.PController;
import com.arcrobotics.ftclib.hardware.motors.Motor;

public class MotorController {
    private final Motor motor;
    private int target;
    private final double maxPower;
    private final int tolerance;

    public MotorController(Motor motor, double kp, int tolerance, double maxPower) {
        this.motor = motor;
        this.maxPower = maxPower;
        this.tolerance = tolerance;

        motor.setRunMode(Motor.RunMode.RawPower);
        motor.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        setTargetPosition(0);
    }

    public void setTargetPosition(int position) {
        // motor.setTargetPosition(position);
        target = position;
    }

    public double getPower() {
        int distance = target - motor.getCurrentPosition();
        if (Math.abs(distance) > tolerance) {
            return Math.min(maxPower, distance * maxPower * motor.getPositionCoefficient());
        } else {
            return 0; // Brake motor.
        }
    }

    public int getTargetPosition() {
        return target;
    }
}
