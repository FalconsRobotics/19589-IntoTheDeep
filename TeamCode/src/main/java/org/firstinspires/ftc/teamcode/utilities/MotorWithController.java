package org.firstinspires.ftc.teamcode.utilities;

import com.arcrobotics.ftclib.controller.PIDFController;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.hardware.HardwareMap;

// TODO: ALL OF THIS NEEDS CLEANED UP AFTER 1ST COMPETITION

public class MotorWithController {
    public final Motor motor;
    public final PIDFController controller;
    public final double maxPower;

    public MotorWithController(HardwareMap map, String name, double kp, double ki, double kd, double kf, int tolerance, double maxPower) {
        this.motor = new Motor(map, name);
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
}
