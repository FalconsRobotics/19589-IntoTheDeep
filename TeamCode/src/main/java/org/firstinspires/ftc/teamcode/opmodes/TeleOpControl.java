package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.opmodes.util.*;

/** Main TeleOp Mode */
@TeleOp
public abstract class TeleOpControl extends OpMode {

    /** Access to all subsystems */
    private final SubsystemsCollection sys = new SubsystemsCollection(hardwareMap);

    private final GamepadWrapper pad1 = new GamepadWrapper(gamepad1);
    private final GamepadWrapper pad2 = new GamepadWrapper(gamepad2);

    /** Entry point of OpMode */
    public void init() {

    }

    // init_loop() will go here, if needed.

    /** Code run when drive team presses "Start" on the control hub. */
    public void start() {

    }

    /** Main loop. Controls go here! */
    public void loop() {

    }

    /** Called once at end of operation. */
    public void stop() {

    }
}
