package org.firstinspires.ftc.teamcode.opmodes;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.ParallelDeadlineGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.commands.*;
import org.firstinspires.ftc.teamcode.utilities.DeltaTime;
import org.firstinspires.ftc.teamcode.utilities.SubsystemsCollection;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Extake;
import org.firstinspires.ftc.teamcode.utilities.vision.VisionUtility;

/** Main Tele-Op to be used during competition matches and drive practice. */
@TeleOp(name = "Command TeleOp")
public class CommandTeleOp extends CommandOpMode {
    // NOTE TO SELF: whenActive() is NOT THE SAME AS whileActiveOnce()!!!!!!!
    private SubsystemsCollection sys;
    private GamepadEx driverGamepad, utilityGamepad;
    private DeltaTime deltaTime;

    // For finer drive base control
    private double driveSpeedMultiplier = 1.0;
    private double driveRotationMultiplier = 1.0;

    // TODO: Better way to do this, at least more understandable.

    /** @note: false applied to `stepDown` implies you want to step up. */
    private void updateDriveSpeedAndRotationMultipliers(boolean stepDown) {
        final double DRIVE_ROTATION_MINIMUM = 0.65;
        final double DRIVE_SPEED_MINIMUM = 0.6;
        final int DRIVE_MULTIPLIER_DECREMENT_STAGES = 2;

        final double SPEED_DECREMENT = (1.0 - DRIVE_SPEED_MINIMUM) / DRIVE_MULTIPLIER_DECREMENT_STAGES;
        final double ROTATION_DECREMENT = (1.0 - DRIVE_ROTATION_MINIMUM) / DRIVE_MULTIPLIER_DECREMENT_STAGES;

        // Since both decrement values for rotation and speed multipliers will equal
        // one minus their respective minimum speeds after 4 iterations, we need to only check one.
        // (hopefully)
        if (stepDown && driveRotationMultiplier <= DRIVE_ROTATION_MINIMUM) {
            driverGamepad.gamepad.rumble(1000);
            return;
        }

        if (!stepDown && driveRotationMultiplier >= 1.0) {
            driverGamepad.gamepad.rumble(1000);
            return;
        }

        driveRotationMultiplier -= (stepDown) ? ROTATION_DECREMENT : -ROTATION_DECREMENT;
        driveSpeedMultiplier -= (stepDown) ? SPEED_DECREMENT : -SPEED_DECREMENT;

        int blips = (int) Math.round(((driveRotationMultiplier - DRIVE_ROTATION_MINIMUM) / ROTATION_DECREMENT) + 1);
        driverGamepad.gamepad.rumbleBlips(blips);
    }

    /** updates slide position according to `input`. */
    private void updateSlidePosition(double input) {
        final double SPEED_MULTIPLIER = (Intake.SlidePosition.RETRACTED - Intake.SlidePosition.EXTENDED) / 0.22;
        sys.intake.moveSlidePosition(input * SPEED_MULTIPLIER * deltaTime.get());
        // Extended position is a lower number than retracted.
        sys.intake.setSlidePosition(sys.intake.leftSlide.clamp(Intake.SlidePosition.EXTENDED, Intake.SlidePosition.RETRACTED));
    }

    /** updates pivot position according to `input`. */
    private void updatePivotPosition(double input) {
        final double SPEED_MULTIPLIER = (Intake.PivotPosition.LEFT - Intake.PivotPosition.RIGHT) / 0.6;
        sys.intake.pivot.moveServoPosition(input * SPEED_MULTIPLIER * deltaTime.get());
        sys.intake.pivot.clamp(Intake.PivotPosition.RIGHT, Intake.PivotPosition.LEFT);
    }


    /**
     *  Entry point of OpMode.
     */
    public void initialize() {
        SubsystemsCollection.deinit();
        sys = SubsystemsCollection.getInstance(hardwareMap);
        VisionUtility vision = new VisionUtility(hardwareMap);
        driverGamepad = new GamepadEx(gamepad1);
        utilityGamepad = new GamepadEx(gamepad2);
        deltaTime = new DeltaTime();

        sys.driveBase.useExternalDriveCommands = false;

        schedule(new CommandRun(() -> {
            sys.driveBase.odometry.update();

            sys.driveBase.driveRobotCentric = true;
            sys.driveBase.motorPowers = new Pose2d(
                    driverGamepad.getLeftY() * driveSpeedMultiplier,
                    driverGamepad.getLeftX() * driveSpeedMultiplier,
                    driverGamepad.getRightX() * driveRotationMultiplier
            );

            sys.driveBase.brake(
                    driverGamepad.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER) != 1.0
            );

            updateSlidePosition(utilityGamepad.getRightY());
            updatePivotPosition(-utilityGamepad.getLeftX());

            // REALLY REALLY BAD HACK!!!
            if (sys.intake.arm.controller.getSetPoint() == Intake.ArmPosition.PICKUP &&
                    sys.intake.sampleColor.getDistance(DistanceUnit.CM) < 2.25) {
                utilityGamepad.gamepad.rumble(100);
            }

            // Telemetry
            telemetry.addData("X Pos (mm)", sys.driveBase.odometry.getPosX());
            telemetry.addData("Y Pos (mm)", sys.driveBase.odometry.getPosY());
            telemetry.addData("Heading (deg)",
                    sys.driveBase.odometry.getHeading() * (180 / Math.PI));
            telemetry.addLine();
            telemetry.addData("Intake Arm Motor Power", sys.intake.arm.motor.get());
            telemetry.addLine();
            telemetry.addData("Extake Lift Motor Power", sys.extake.lift.motor.get());
            telemetry.addData("Extake Secondary Lift Motor Power", sys.extake.lift.motor.get());
            telemetry.addLine();
            telemetry.addLine("Drivebase wheel powers:");
            telemetry.addLine(sys.driveBase.mDirect.frontLeft.get() + "\t" + sys.driveBase.mDirect.frontRight.get());
            telemetry.addLine(sys.driveBase.mDirect.backLeft.get() + "\t" + sys.driveBase.mDirect.backRight.get());
            telemetry.update();

            deltaTime.update();
            return false; // This should never finish.
        }));

        /*
         * Driver Controls
         */

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

        driverGamepad.getGamepadButton(GamepadKeys.Button.DPAD_UP)
            .whileActiveContinuous(
                new CommandDriveBaseDriveFieldCentric(
                    () -> 0,
                    () -> driveSpeedMultiplier,
                    () -> 0
                )
            );

        driverGamepad.getGamepadButton(GamepadKeys.Button.DPAD_DOWN)
            .whileActiveContinuous(
                new CommandDriveBaseDriveFieldCentric(
                    () -> 0,
                    () -> -driveSpeedMultiplier,
                    () -> 0
                )
            );

        driverGamepad.getGamepadButton(GamepadKeys.Button.DPAD_RIGHT)
            .whileActiveContinuous(
                new CommandDriveBaseDriveFieldCentric(
                    () -> driveSpeedMultiplier,
                    () -> 0,
                    () -> 0
                )
            );

        driverGamepad.getGamepadButton(GamepadKeys.Button.DPAD_LEFT)
            .whileActiveContinuous(
                new CommandDriveBaseDriveFieldCentric(
                    () -> -driveSpeedMultiplier,
                    () -> 0,
                    () -> 0
                )
            );

        driverGamepad.getGamepadButton(GamepadKeys.Button.Y)
            .whileActiveContinuous(
                new CommandDriveBaseLockRotation(0.0)
            );

        driverGamepad.getGamepadButton(GamepadKeys.Button.X)
            .whileActiveContinuous(
                new CommandDriveBaseLockRotation(Math.PI / 2)
            );

        driverGamepad.getGamepadButton(GamepadKeys.Button.A)
            .whileActiveContinuous(
                new CommandDriveBaseLockRotation(Math.PI)
            );

        driverGamepad.getGamepadButton(GamepadKeys.Button.B)
            .whileActiveContinuous(
                new CommandDriveBaseLockRotation(Math.PI * 3 / 2)
            );

        driverGamepad.getGamepadButton(GamepadKeys.Button.START)
            .whenActive(
                new CommandDriveBaseRecalibrate(),false
            );

        /*
         * Utility Controls
         */

        utilityGamepad.getGamepadButton(GamepadKeys.Button.RIGHT_STICK_BUTTON)
            .whenActive(
                new CommandIntakeSetSlide(Intake.SlidePosition.RETRACTED)
            );

        utilityGamepad.getGamepadButton(GamepadKeys.Button.LEFT_STICK_BUTTON)
            .whenActive(
                new CommandIntakeSetPivot(Intake.PivotPosition.MIDDLE)
            );

        utilityGamepad.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER)
            .whenActive(
                new CommandIntakeRotateWheels(Intake.WheelPower.UNLOAD, 500)
            );

        utilityGamepad.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER)
            .whenActive(
                new CommandIntakeRotateWheels(Intake.WheelPower.LOAD, 500)
            );

        utilityGamepad.getGamepadButton(GamepadKeys.Button.DPAD_LEFT)
            .whenActive(new SequentialCommandGroup(
                new ParallelDeadlineGroup(
                    new CommandExtakeSetLift(Extake.LiftPosition.DOWN),
                    new CommandExtakeSetBucket(Extake.BucketPosition.LOAD),
                    new CommandIntakeSetArm(Intake.ArmPosition.IDLE),
                    new CommandIntakeSetSlide(Intake.SlidePosition.RETRACTED),
                    new CommandIntakeSetPivot(Intake.PivotPosition.MIDDLE)
                ),
                new CommandIntakeSetArm(Intake.ArmPosition.UNLOAD),
                new CommandTimer(125),
                new CommandIntakeRotateWheels(Intake.WheelPower.UNLOAD, 500)
            ));

        utilityGamepad.getGamepadButton(GamepadKeys.Button.DPAD_RIGHT)
            .whenPressed(new ParallelDeadlineGroup(
                    new CommandTimer(100), // Because sometimes arm won't reach position.
                    new CommandIntakeSetArm(Intake.ArmPosition.HOVER)
            ), false);

        utilityGamepad.getGamepadButton(GamepadKeys.Button.DPAD_DOWN)
            .whenActive(new ParallelDeadlineGroup (
                    new CommandIntakeRotateWheels(Intake.WheelPower.LOAD, 800), // Same thing here.
                    new CommandIntakeSetArm(Intake.ArmPosition.PICKUP)
            ), false);

        utilityGamepad.getGamepadButton(GamepadKeys.Button.DPAD_UP)
            .whenActive(new ParallelCommandGroup(
                new CommandExtakeSetBucket(Extake.BucketPosition.LOAD),
                new CommandIntakeSetArm(Intake.ArmPosition.IDLE),
                new CommandIntakeSetPivot(Intake.PivotPosition.MIDDLE),
                new CommandIntakeSetSlide(Intake.SlidePosition.RETRACTED),
                new CommandExtakeSetLift(Extake.LiftPosition.DOWN)
            ));


        utilityGamepad.getGamepadButton(GamepadKeys.Button.A)
            .whenActive(new ParallelCommandGroup(
                new CommandExtakeSetBucket(Extake.BucketPosition.LOAD),
                new CommandIntakeSetArm(Intake.ArmPosition.IDLE),
                new CommandExtakeSetLift(Extake.LiftPosition.LOWER_BAR)
            ));

        utilityGamepad.getGamepadButton(GamepadKeys.Button.X)
            .whenActive(new ParallelCommandGroup(
                new CommandExtakeSetBucket(Extake.BucketPosition.LOAD),
                new CommandIntakeSetArm(Intake.ArmPosition.IDLE),
                new CommandExtakeSetLift(Extake.LiftPosition.TOP_BAR)
            ));

        utilityGamepad.getGamepadButton(GamepadKeys.Button.B)
            .whileActiveOnce(new SequentialCommandGroup(
                new ParallelDeadlineGroup(
                    new CommandExtakeSetLift(Extake.LiftPosition.LOWER_BUCKET),
                    new CommandExtakeSetBucket(Extake.BucketPosition.PREPARE_UNLOAD),
                    new CommandIntakeSetArm(Intake.ArmPosition.IDLE)
                ),
                new CommandExtakeSetBucket(Extake.BucketPosition.UNLOAD)
            ));

        utilityGamepad.getGamepadButton(GamepadKeys.Button.Y)
            .whileActiveOnce(new SequentialCommandGroup(
                new ParallelDeadlineGroup(
                    new CommandExtakeSetLift(Extake.LiftPosition.TOP_BUCKET),
                    new CommandExtakeSetBucket(Extake.BucketPosition.PREPARE_UNLOAD),
                    new CommandIntakeSetArm(Intake.ArmPosition.IDLE)
                ),
                new CommandExtakeSetBucket(Extake.BucketPosition.UNLOAD)
            ));
    }
}
