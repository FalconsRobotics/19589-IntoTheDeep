package org.firstinspires.ftc.teamcode.opmodes.tests.vision;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.ParallelDeadlineGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.commands.CommandAutoStrafe;
import org.firstinspires.ftc.teamcode.commands.CommandExtakeSetBucket;
import org.firstinspires.ftc.teamcode.commands.CommandExtakeSetLift;
import org.firstinspires.ftc.teamcode.commands.CommandIntakeAutoPivot;
import org.firstinspires.ftc.teamcode.commands.CommandIntakeAutoSlide;
import org.firstinspires.ftc.teamcode.commands.CommandIntakeSetArm;
import org.firstinspires.ftc.teamcode.commands.CommandIntakeSetPivot;
import org.firstinspires.ftc.teamcode.commands.CommandIntakeSetSlide;
import org.firstinspires.ftc.teamcode.subsystems.Extake;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.utilities.DeltaTime;
import org.firstinspires.ftc.teamcode.utilities.SubsystemsCollection;
import org.firstinspires.ftc.teamcode.utilities.vision.VisionUtility;

@Disabled
@TeleOp(name = "Auto Pickup Samples", group = "Tests")
public class AutopickupSampleTest extends CommandOpMode {

    public void initialize() {
        SubsystemsCollection.deinit();

        SubsystemsCollection sys = SubsystemsCollection.getInstance(hardwareMap);
        VisionUtility vision = new VisionUtility(hardwareMap);
        GamepadEx driverGamepad = new GamepadEx(gamepad1);
        DeltaTime deltaTime = new DeltaTime();
        waitForStart();



            driverGamepad.getGamepadButton(GamepadKeys.Button.A)
                    .whileActiveOnce(new ParallelCommandGroup(
                            new CommandIntakeAutoPivot(hardwareMap)
                    ));

            telemetry.addData("X: ", vision.findStrafeToBlock());
            telemetry.addData("Y: ", vision.findDistanceToBlock());

            telemetry.update();
        }

}

