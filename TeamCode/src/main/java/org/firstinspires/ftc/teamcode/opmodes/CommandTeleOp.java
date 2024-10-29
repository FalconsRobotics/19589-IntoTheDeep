package org.firstinspires.ftc.teamcode.opmodes;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.Subsystem;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.opmodes.commands.CommandDriveBaseBrake;
import org.firstinspires.ftc.teamcode.opmodes.commands.CommandDriveBaseDriveRobotCentric;
import org.firstinspires.ftc.teamcode.opmodes.commands.CommandExtakeMoveLift;
import org.firstinspires.ftc.teamcode.subsystems.Extake;
import org.firstinspires.ftc.teamcode.subsystems.SubsystemsCollection;

import java.util.Collections;
import java.util.Set;

@TeleOp(name = "Command TeleOp")
public class CommandTeleOp extends CommandOpMode {
    private SubsystemsCollection sys;
    private GamepadEx driverGamepad, utilityGamepad;

    // For finer drive base control.
    private double rotationMultiplier = 1.0, speedMultiplier = 1.0;

    public void initialize() {
        sys = SubsystemsCollection.getInstance(hardwareMap);
        driverGamepad = new GamepadEx(gamepad1);
        utilityGamepad = new GamepadEx(gamepad2);

        // Drive Base commands
        sys.driveBase.brake(driverGamepad.getButton(GamepadKeys.Button.LEFT_BUMPER));

        schedule(new CommandDriveBaseDriveRobotCentric(
                () -> (driverGamepad.getLeftY() +
                        (driverGamepad.getButton(GamepadKeys.Button.DPAD_UP) ? 1.0 : 0.0) -
                        (driverGamepad.getButton(GamepadKeys.Button.DPAD_DOWN) ? 1.0 : 0.0)) *
                        speedMultiplier,

                () -> (driverGamepad.getLeftX() +
                        (driverGamepad.getButton(GamepadKeys.Button.DPAD_RIGHT) ? 1.0 : 0.0) -
                        (driverGamepad.getButton(GamepadKeys.Button.DPAD_LEFT) ? 1.0 : 0.0)) *
                        speedMultiplier,

                () -> driverGamepad.getRightX() * rotationMultiplier
        ));

        driverGamepad.getGamepadButton(GamepadKeys.Button.A)
                .whenPressed(new CommandExtakeMoveLift(Extake.LiftPosition.DOWN));

        driverGamepad.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER)
                .whileActiveOnce(new CommandDriveBaseBrake(true))
                .whenInactive(new CommandDriveBaseBrake(false));
    }
}
