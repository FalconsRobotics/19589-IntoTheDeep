package org.firstinspires.ftc.teamcode.utilities;

import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public abstract class MotorWithController {
    /** Motor accessed through controller. */
    public final Motor motor;

    // Protected to ensure this class cannot be created as itself.
    protected MotorWithController(HardwareMap map, String name) {
        motor = new Motor(map, name);
    }

    /** Implemented to set motor power with control system. */
    public abstract void setMotorPower();
    /** Sets new target for motor to go to, in ticks. */
    public abstract void setTarget(int target);
    /** Returns whether or not motor is at it's desired target. */
    public abstract boolean atTarget();
}
