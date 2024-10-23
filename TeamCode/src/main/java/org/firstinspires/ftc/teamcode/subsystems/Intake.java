package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;


public class Intake extends SubsystemBase {
    public final Servo slide;
    public final Motor arm;

    public Intake(HardwareMap map) {
        slide = map.get(Servo.class, "Intake-Slide");
        arm = new Motor(map, "Intake-Arm");
    }

    public void periodic() {
        // Do nothing... for now...
    }
}
