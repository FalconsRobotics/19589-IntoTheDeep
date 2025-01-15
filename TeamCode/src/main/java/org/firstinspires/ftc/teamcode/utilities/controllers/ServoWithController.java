package org.firstinspires.ftc.teamcode.utilities.controllers;

import com.arcrobotics.ftclib.util.MathUtils;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;


/** Extra functionality on top of base Servo class to allow for movements relative to the servo's
 *  current position. Perhaps not a perfect class name but TOO BAD! */
public class ServoWithController {
    /** Servo accessed by class */
    public final Servo servo;

    public ServoWithController(HardwareMap map, String name) {
        servo = map.get(Servo.class, name);
    }

    /** Moves servo to a given `offset` from its current position. */
    public void moveServoPosition(double offset) {
        double position = (servo.getPosition() != Double.NaN) ? servo.getPosition() : 0;
        servo.setPosition(position + offset);
    }

    /** Clamps current servo position if it is already outside the `min` and `max` range. Returns
     *  clamped position. */
    public double clamp(double min, double max) {
        servo.setPosition(MathUtils.clamp(servo.getPosition(), min, max));
        return servo.getPosition();
    }
}
