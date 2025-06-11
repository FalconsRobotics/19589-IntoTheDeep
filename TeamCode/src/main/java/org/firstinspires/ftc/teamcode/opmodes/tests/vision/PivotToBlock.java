package org.firstinspires.ftc.teamcode.opmodes.tests.vision;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
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
import org.firstinspires.ftc.teamcode.utilities.Clamp;
import org.firstinspires.ftc.teamcode.utilities.DeltaTime;
import org.firstinspires.ftc.teamcode.utilities.SubsystemsCollection;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Extake;

import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.utilities.vision.VisionUtility;

@Disabled
@TeleOp(name = "Pivot to Block Test", group = "Tests")
public class PivotToBlock extends LinearOpMode {
    public void runOpMode() {
        SubsystemsCollection.deinit();
        SubsystemsCollection sys = SubsystemsCollection.getInstance(hardwareMap);

        VisionUtility vision = new VisionUtility(hardwareMap);
        double distance = vision.findDistanceToBlock();
        double position = 1;

        waitForStart();

        while (opModeIsActive()) {
//            double angle = vision.findBlockAngle(0);
//
//            if(angle <= 6 || angle >= 170) {
//                sys.intake.pivot.servo.setPosition(Intake.PivotPosition.MIDDLE);
//                telemetry.addData("Angle: ", angle);
//            } else if(angle > 6 && angle <= 80) {
//                sys.intake.pivot.servo.setPosition(Intake.PivotPosition.LEFT);
//                telemetry.addData("Angle: ", angle);
//            } else if(angle > 80 && angle <= 120) {
//                sys.intake.pivot.servo.setPosition(Intake.PivotPosition.RIGHT);
//                telemetry.addData("Angle: ", angle);
//            } else if(angle > 120 && angle < 170){
//                sys.intake.pivot.servo.setPosition(Intake.PivotPosition.MIDDLE);
//                telemetry.addData("Angle: ", angle);
//            }
//




            while(distance > 2 || distance < -2){
                position -= .001 * Math.signum(distance);
                sys.intake.setSlidePosition(position);
                //sys.intake.setSlidePosition(Clamp.clamp(sys.intake.leftSlide.servo.getPosition(), Intake.SlidePosition.EXTENDED, Intake.SlidePosition.RETRACTED));
                distance = vision.findDistanceToBlock();
                telemetry.addData("position: ", position);
                telemetry.addData("Y: ", vision.findDistanceToBlock());
                telemetry.update();
            }


            telemetry.update();
        }
    }
}