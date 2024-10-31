package org.firstinspires.ftc.teamcode.opmodes;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.Subsystem;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.opmodes.commands.CommandDriveBaseBrake;
import org.firstinspires.ftc.teamcode.opmodes.commands.CommandDriveBaseDriveRobotCentric;
import org.firstinspires.ftc.teamcode.opmodes.commands.CommandExtakeMoveLift;
import org.firstinspires.ftc.teamcode.opmodes.commands.CommandExtakeRotateArm;
import org.firstinspires.ftc.teamcode.opmodes.commands.CommandRunContinuous;
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

        // Driver gamepad controls.

        // Genuinely the most unreadable code I've ever made. TOO BAD!
        schedule(new ParallelCommandGroup(
            new CommandDriveBaseDriveRobotCentric(
                    () -> (driverGamepad.getLeftY() +
                            (driverGamepad.getButton(GamepadKeys.Button.DPAD_UP) ? 1.0 : 0.0) -
                            (driverGamepad.getButton(GamepadKeys.Button.DPAD_DOWN) ? 1.0 : 0.0)) *
                            speedMultiplier,

                    () -> (driverGamepad.getLeftX() +
                            (driverGamepad.getButton(GamepadKeys.Button.DPAD_RIGHT) ? 1.0 : 0.0) -
                            (driverGamepad.getButton(GamepadKeys.Button.DPAD_LEFT) ? 1.0 : 0.0)) *
                            speedMultiplier,

                    () -> driverGamepad.getRightX() * rotationMultiplier
            ), new CommandRunContinuous(
                    () -> {
                        rotationMultiplier = 1.0;
                        speedMultiplier = 1.0;

                        if (driverGamepad.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) == 1) {
                            rotationMultiplier = 0.75;
                        } else if (driverGamepad.getButton(GamepadKeys.Button.RIGHT_BUMPER)) {
                            rotationMultiplier = 0.5;
                        }

                        if (driverGamepad.getButton(GamepadKeys.Button.Y)) {
                            speedMultiplier = 0.75;
                        } else if (driverGamepad.getButton(GamepadKeys.Button.X)) {
                            speedMultiplier = 0.5;
                        } else if (driverGamepad.getButton(GamepadKeys.Button.A)) {
                            speedMultiplier = 0.25;
                        }

                        return false;
                    }
        )));

        driverGamepad.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER)
                .whileActiveOnce(new CommandDriveBaseBrake(true))
                .whenInactive(new CommandDriveBaseBrake(false));

        // Utility gamepad controls

        utilityGamepad.getGamepadButton(GamepadKeys.Button.DPAD_DOWN)
                .whileActiveOnce(new ParallelCommandGroup(
                        new CommandExtakeMoveLift(Extake.LiftPosition.DOWN),
                        new CommandExtakeRotateArm(Extake.ArmPosition.LOAD)
                ));
    }
}
