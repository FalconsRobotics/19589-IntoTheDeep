package org.firstinspires.ftc.teamcode.opmodes;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.ParallelDeadlineGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.util.MathUtils;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.opmodes.commands.CommandDriveBaseBrake;
import org.firstinspires.ftc.teamcode.opmodes.commands.CommandExtakeMoveLift;
import org.firstinspires.ftc.teamcode.opmodes.commands.CommandExtakeRotateArm;
import org.firstinspires.ftc.teamcode.opmodes.commands.CommandIntakeRotateArm;
import org.firstinspires.ftc.teamcode.opmodes.commands.CommandIntakeRotateWheels;
import org.firstinspires.ftc.teamcode.opmodes.commands.CommandRunContinuous;
import org.firstinspires.ftc.teamcode.opmodes.commands.CommandTimer;
import org.firstinspires.ftc.teamcode.subsystems.Extake;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.utilities.SubsystemsCollection;

@TeleOp(name = "Command TeleOp")
public class CommandTeleOp extends CommandOpMode {
    private SubsystemsCollection sys;
    private GamepadEx driverGamepad, utilityGamepad;

    // For finer drive base control.
    private double driveRotationMultiplier = 1.0, driveSpeedMultiplier = 1.0;

    // For controlling intake slides
    private double intakeSlideAccumulator = Intake.SlidePosition.RETRACTED;
    private final double INTAKE_SLIDE_MAX_ACCUMULATION = 0.01;

    public void initialize() {
        SubsystemsCollection.deinit();
        sys = SubsystemsCollection.getInstance(hardwareMap);
        driverGamepad = new GamepadEx(gamepad1);
        utilityGamepad = new GamepadEx(gamepad2);

        // All code that needs to run continuously
        // Genuinely the most unreadable code I've ever made. TOO BAD!
        schedule(new CommandRunContinuous(() -> {
            // Driver gamepad controls

            driveRotationMultiplier = 1.0;
            driveSpeedMultiplier = 1.0;

            if (driverGamepad.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) == 1) {
                driveRotationMultiplier = 0.75;
            } else if (driverGamepad.getButton(GamepadKeys.Button.RIGHT_BUMPER)) {
                driveRotationMultiplier = 0.5;
            }

            if (driverGamepad.getButton(GamepadKeys.Button.Y)) {
                driveSpeedMultiplier = 0.75;
            } else if (driverGamepad.getButton(GamepadKeys.Button.X)) {
                driveSpeedMultiplier = 0.5;
            } else if (driverGamepad.getButton(GamepadKeys.Button.A)) {
                driveSpeedMultiplier = 0.25;
            }

            if (driverGamepad.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER) == 1) {
                driveSpeedMultiplier = 0.65;
                driveRotationMultiplier = 0.65;
            }

            // Equations for driving
            double driveX = (driverGamepad.getLeftX() +
                    (driverGamepad.getButton(GamepadKeys.Button.DPAD_RIGHT) ? 1.0 : 0.0) -
                    (driverGamepad.getButton(GamepadKeys.Button.DPAD_LEFT) ? 1.0 : 0.0)) *
                            driveSpeedMultiplier;
            double driveY = (driverGamepad.getLeftY() +
                    (driverGamepad.getButton(GamepadKeys.Button.DPAD_UP) ? 1.0 : 0.0) -
                    (driverGamepad.getButton(GamepadKeys.Button.DPAD_DOWN) ? 1.0 : 0.0)) *
                            driveSpeedMultiplier;

            sys.driveBase.motors.driveRobotCentric(
                    driveX, driveY,
                    driverGamepad.getRightX() * driveRotationMultiplier,
                    true
            );

            // Utility gamepad controls

            intakeSlideAccumulator += INTAKE_SLIDE_MAX_ACCUMULATION * utilityGamepad.getRightY();
            // Intake slide is retracted at ~0.9 and extended at ~0.4, hence why retracted is
            // considered its maximum position.
            intakeSlideAccumulator = MathUtils.clamp(intakeSlideAccumulator,
                    Intake.SlidePosition.EXTENDED, Intake.SlidePosition.RETRACTED
            );

            if (utilityGamepad.getButton(GamepadKeys.Button.RIGHT_STICK_BUTTON)) {
                intakeSlideAccumulator = Intake.SlidePosition.RETRACTED;
            }

            sys.intake.setSlidePosition(intakeSlideAccumulator);

            telemetry.addData("Slide Accumulator", intakeSlideAccumulator);
            telemetry.addData("Y Pos", utilityGamepad.getLeftY());
            telemetry.update();

            return false; // This should never finish.
        }));

        // Driver gamepad controls

        driverGamepad.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER)
                .whileActiveContinuous(new CommandDriveBaseBrake(true))
                .whenInactive(new CommandDriveBaseBrake(false));


        // Utility gamepad controls

        utilityGamepad.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER)
                .whileActiveOnce(
                        new CommandIntakeRotateWheels(-1, 800)
                );

        utilityGamepad.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER)
                .whileActiveOnce(
                        new CommandIntakeRotateWheels(1, 800)
                );

        utilityGamepad.getGamepadButton(GamepadKeys.Button.DPAD_LEFT)
                .whileActiveOnce(new SequentialCommandGroup(
                        new ParallelDeadlineGroup(
                                new CommandExtakeMoveLift(Extake.LiftPosition.DOWN),
                                new CommandIntakeRotateArm(Intake.ArmPosition.IDLE),
                                new CommandExtakeRotateArm(Extake.ArmPosition.LOAD)
                        ),
                        new CommandIntakeRotateArm(Intake.ArmPosition.UNLOAD)
                ));

        utilityGamepad.getGamepadButton(GamepadKeys.Button.DPAD_RIGHT)
                .whileActiveOnce(new CommandIntakeRotateArm(Intake.ArmPosition.HOVER));

        utilityGamepad.getGamepadButton(GamepadKeys.Button.DPAD_DOWN)
                .whileActiveOnce(new SequentialCommandGroup(
                        new ParallelDeadlineGroup(
                                new CommandIntakeRotateWheels(1, 1200),
                                new CommandIntakeRotateArm(Intake.ArmPosition.PICKUP)
                        ),
                        new CommandIntakeRotateArm(Intake.ArmPosition.HOVER)
                ));

        utilityGamepad.getGamepadButton(GamepadKeys.Button.DPAD_UP)
                .whileActiveOnce(new ParallelCommandGroup(
                        new CommandExtakeRotateArm(Extake.ArmPosition.LOAD),
                        new CommandIntakeRotateArm(Intake.ArmPosition.IDLE),
                        new CommandExtakeMoveLift(Extake.LiftPosition.DOWN)
                ));


        utilityGamepad.getGamepadButton(GamepadKeys.Button.A)
                .whileActiveOnce(new ParallelCommandGroup(
                        new CommandExtakeRotateArm(Extake.ArmPosition.LOAD),
                        new CommandIntakeRotateArm(Intake.ArmPosition.IDLE),
                        new CommandExtakeMoveLift(Extake.LiftPosition.LOWER_BAR)
                ));

        utilityGamepad.getGamepadButton(GamepadKeys.Button.X)
                .whileActiveOnce(new ParallelCommandGroup(
                        new CommandExtakeRotateArm(Extake.ArmPosition.LOAD),
                        new CommandIntakeRotateArm(Intake.ArmPosition.IDLE),
                        new CommandExtakeMoveLift(Extake.LiftPosition.TOP_BAR)
                ));

        utilityGamepad.getGamepadButton(GamepadKeys.Button.B)
                .whileActiveOnce(new SequentialCommandGroup(
                        new ParallelDeadlineGroup(
                                new CommandExtakeMoveLift(Extake.LiftPosition.LOWER_BUCKET),
                                new CommandExtakeRotateArm(Extake.ArmPosition.PREPARE_UNLOAD),
                                new CommandIntakeRotateArm(Intake.ArmPosition.IDLE)
                        ),
                        new CommandExtakeRotateArm(Extake.ArmPosition.UNLOAD)
                ));

        utilityGamepad.getGamepadButton(GamepadKeys.Button.Y)
                .whileActiveOnce(new SequentialCommandGroup(
                        new ParallelDeadlineGroup(
                                new CommandExtakeMoveLift(Extake.LiftPosition.TOP_BUCKET),
                                new CommandExtakeRotateArm(Extake.ArmPosition.PREPARE_UNLOAD),
                                new CommandIntakeRotateArm(Intake.ArmPosition.IDLE)
                        ),
                        new CommandExtakeRotateArm(Extake.ArmPosition.UNLOAD)
                ));
    }
}
