package org.firstinspires.ftc.teamcode.opmodes.tests.vision;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.ParallelDeadlineGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.commands.*;
import org.firstinspires.ftc.teamcode.utilities.DeltaTime;
import org.firstinspires.ftc.teamcode.utilities.SubsystemsCollection;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Extake;

import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.utilities.vision.VisionUtility;

@TeleOp(name = "Pivot to Block Test", group = "Tests")
public class PivotToBlock extends LinearOpMode {
    public void runOpMode() {
        VisionUtility vision = new VisionUtility(hardwareMap);
        waitForStart();

        while (opModeIsActive()) {
            double angle = vision.findBlockAngle();

            if(angle <= 6 || angle >= 170) {
                new CommandIntakeSetPivot(Intake.PivotPosition.MIDDLE);
                telemetry.addData("Angle: ", angle);
            } else if(angle > 6 && angle <= 80) {
                new CommandIntakeSetPivot(Intake.PivotPosition.LEFT);
                telemetry.addData("Angle: ", angle);
            } else if(angle > 80 && angle <= 120) {
                new CommandIntakeSetPivot(Intake.PivotPosition.RIGHT);
                telemetry.addData("Angle: ", angle);
            } else if(angle > 120 && angle < 170){
                new CommandIntakeSetPivot(Intake.PivotPosition.MIDDLE);
                telemetry.addData("Angle: ", angle);
            }

            if(vision.findDistanceToBlock() > 2 || vision.findDistanceToBlock() < 2){
                new CommandIntakeSetSlide(vision.findDistanceToBlock())
            }
            telemetry.update();
        }
    }
}