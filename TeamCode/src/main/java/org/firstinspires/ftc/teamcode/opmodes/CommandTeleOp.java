package org.firstinspires.ftc.teamcode.opmodes;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.ParallelDeadlineGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.util.MathUtils;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.opmodes.commands.*;
import org.firstinspires.ftc.teamcode.utilities.SubsystemsCollection;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Extake;

/** Main Tele-Op to be used during competition matches and drive practice. */
@TeleOp(name = "Command TeleOp")
public class CommandTeleOp extends CommandOpMode {
    private SubsystemsCollection sys;
    private GamepadEx driverGamepad, utilityGamepad;

    // For finer drive base control
    private double driveSpeedMultiplier = 1.0;
    private double driveRotationMultiplier = 1.0;

    // For controlling intake slides
    private double intakeSlideAccumulator = Intake.SlidePosition.RETRACTED;


    // TODO: Better way to do this, at least more understandable.

    /** @note: false applied to `stepDown` implies you want to step up. */
    private void updateDriveSpeedAndRotationMultipliers(boolean stepDown) {
        final double DRIVE_ROTATION_MINIMUM = 0.6;
        final double DRIVE_SPEED_MINIMUM = 0.5;
        final int DRIVE_MULTIPLIER_DECREMENT_STAGES = 4;

        // Since both decrement values for rotation and speed multipliers will equal
        // one minus their respective minimum speeds after 4 iterations, we need to only check one.
        if (stepDown && driveRotationMultiplier == DRIVE_ROTATION_MINIMUM) {
            driveRotationMultiplier = 1.0;
            driveSpeedMultiplier = 1.0;
            return;
        }

        if (!stepDown && driveRotationMultiplier == 1.0) {
            driveRotationMultiplier = DRIVE_ROTATION_MINIMUM;
            driveSpeedMultiplier = DRIVE_SPEED_MINIMUM;
            return;
        }

        double speedDec = 1.0 - DRIVE_SPEED_MINIMUM / DRIVE_MULTIPLIER_DECREMENT_STAGES;
        double rotationDec = 1.0 - DRIVE_ROTATION_MINIMUM / DRIVE_MULTIPLIER_DECREMENT_STAGES;
        driveRotationMultiplier -= (stepDown) ? speedDec : -speedDec;
        driveSpeedMultiplier -= (stepDown) ? speedDec : -rotationDec;

        int blips = (int) ((driveRotationMultiplier - DRIVE_ROTATION_MINIMUM) / rotationDec);
        driverGamepad.gamepad.rumbleBlips(blips);
    }

    /** Updates slide accumulator according to inputs from driverGamepad. */
    private void updateSlideAccumulator() {
        final double INTAKE_SLIDE_MAX_ACCUMULATION = 0.01;

        intakeSlideAccumulator += INTAKE_SLIDE_MAX_ACCUMULATION * utilityGamepad.getRightY();
        // Intake slide is retracted at ~0.9 and extended at ~0.4, hence why retracted is considered
        // its maximum position.
        intakeSlideAccumulator = MathUtils.clamp(intakeSlideAccumulator,
                Intake.SlidePosition.EXTENDED, Intake.SlidePosition.RETRACTED
        );

        if (utilityGamepad.getButton(GamepadKeys.Button.RIGHT_STICK_BUTTON)) {
            intakeSlideAccumulator = Intake.SlidePosition.RETRACTED;
        }
    }


    /**
     *  Entry point of OpMode.
     */
    public void initialize() {
        SubsystemsCollection.deinit();
        sys = SubsystemsCollection.getInstance(hardwareMap);
        driverGamepad = new GamepadEx(gamepad1);
        utilityGamepad = new GamepadEx(gamepad2);

        // All code that needs to run continuously
        // Genuinely the most unreadable code I've ever made. TOO BAD!
        schedule(new ParallelCommandGroup(
                new CommandRun(() -> {
                        updateSlideAccumulator();
                        sys.driveBase.brake(
                                (driverGamepad.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER) == 1)
                        );

                        telemetry.addData("X (mm)", sys.driveBase.odometry.getPosX());
                        telemetry.addData("Y (mm)", sys.driveBase.odometry.getPosY());
                        telemetry.addData("Heading (deg)",
                                sys.driveBase.odometry.getHeading() * (180 / Math.PI));
                        telemetry.update();

                        return false; // This should never finish.
                }),

                new CommandDriveBaseDriveRobotCentric(
                        () -> driverGamepad.getLeftX() * driveSpeedMultiplier,
                        () -> driverGamepad.getLeftY() * driveSpeedMultiplier,
                        () -> driverGamepad.getRightX() * driveRotationMultiplier
                ),

                new CommandIntakeMoveSlide(
                        () -> intakeSlideAccumulator
                )
        ));

        // Driver gamepad buttons

        driverGamepad.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER)
                .whileActiveOnce(new CommandRun(() -> {
                    updateDriveSpeedAndRotationMultipliers(true);
                    return true;
                }));

        driverGamepad.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER)
                .whileActiveOnce(new CommandRun(() -> {
                    updateDriveSpeedAndRotationMultipliers(false);
                    return true;
                }));

        driverGamepad.getGamepadButton(GamepadKeys.Button.START)
                        .whileActiveOnce(new CommandDriveBaseRecalibrateIMU(), false);
        

        // Utility gamepad controls

        utilityGamepad.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER)
                .whileActiveOnce(
                        new CommandIntakeRotateWheels(Intake.WheelPower.UNLOAD, 800)
                );

        utilityGamepad.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER)
                .whileActiveOnce(
                        new CommandIntakeRotateWheels(Intake.WheelPower.LOAD, 800)
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
                                new CommandIntakeRotateWheels(Intake.WheelPower.LOAD, 1200),
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
