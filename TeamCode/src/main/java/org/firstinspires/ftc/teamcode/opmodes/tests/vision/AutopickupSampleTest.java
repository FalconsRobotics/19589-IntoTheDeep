package org.firstinspires.ftc.teamcode.opmodes.tests.vision;

import com.arcrobotics.ftclib.command.ParallelDeadlineGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.commands.CommandAutoStrafe;
import org.firstinspires.ftc.teamcode.commands.CommandIntakeAutoPivot;
import org.firstinspires.ftc.teamcode.commands.CommandIntakeAutoSlide;
import org.firstinspires.ftc.teamcode.commands.CommandIntakeSetArm;
import org.firstinspires.ftc.teamcode.commands.CommandIntakeSetSlide;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.utilities.SubsystemsCollection;
import org.firstinspires.ftc.teamcode.utilities.vision.VisionUtility;

@TeleOp(name = "Auto Pickup Samples", group = "Tests")
public class AutopickupSampleTest extends LinearOpMode {

    private GamepadEx driverGamepad;
    public void runOpMode() {
        SubsystemsCollection.deinit();
        SubsystemsCollection sys = SubsystemsCollection.getInstance(hardwareMap);

        VisionUtility vision = new VisionUtility(hardwareMap);
        driverGamepad = new GamepadEx(gamepad1);
        waitForStart();

        while (opModeIsActive()) {

            driverGamepad = new GamepadEx(gamepad1);

            driverGamepad.getGamepadButton(GamepadKeys.Button.B)
                            .whileActiveOnce(new SequentialCommandGroup(
                                    new ParallelDeadlineGroup(
                                        new CommandAutoStrafe(hardwareMap),
                                        new CommandIntakeAutoSlide(hardwareMap),
                                        new CommandIntakeAutoPivot(hardwareMap)
                                    )));

            driverGamepad.getGamepadButton(GamepadKeys.Button.A)
                            .whileActiveOnce(new SequentialCommandGroup(
                                    new ParallelDeadlineGroup(
                                    new CommandIntakeSetSlide(Intake.SlidePosition.RETRACTED),
                                    new CommandIntakeSetArm(Intake.ArmPosition.HOVER)
                            )));


            telemetry.update();
        }
    }
}

