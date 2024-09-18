package org.firstinspires.ftc.teamcode.common.drive.roadrunner.samples.TeleOp.Auto;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.common.drive.roadrunner.MecanumDrive;

@Autonomous(name = "SimpleRRSample", group = "Samples")
public class SimpleRRSample extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        MecanumDrive drive = new MecanumDrive(hardwareMap, new Pose2d(0,0,0));

        DcMotor motor1 = hardwareMap.get(DcMotor.class,  "motor");

        // Declare Trajectory as such
        Action TrajectoryAction1 = drive.actionBuilder(drive.pose)
                .lineToX(10)
                .build();

        //Declare a second trajectory
        Action TrajectoryAction2 = drive.actionBuilder(new Pose2d(15,20,0))
                .splineTo(new Vector2d(5,5), Math.toRadians(90))
                .build();

        while(!isStopRequested() && !opModeIsActive()) {
        }

        Actions.runBlocking(
                //will run each action in a row
                new SequentialAction(
                        TrajectoryAction1,
                        new Action(){
                            @Override
                            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                                telemetry.addLine("Action!");
                                telemetry.update();
                                return false;
                            }
                        },
                        //Lambda expression
                        (telemetryPacket) -> {
                            telemetry.addLine("Action!");
                            telemetry.update();
                            return false; // Returning true causes the action to run again, returning false causes it to cease
                        },
                        new ParallelAction( // several actions being run in parallel
                                TrajectoryAction2, // Run second trajectory
                                (telemetryPacket) -> { // Run some action
                                    motor1.setPower(1);
                                    return false;
                                }
                        ),
                        drive.actionBuilder(new Pose2d(15,10,Math.toRadians(125))) // Another way of running a trajectory (not recommended because trajectories take time to build and will slow down your code, always try to build them beforehand)
                                .splineTo(new Vector2d(25, 15), 0)
                                .build()
                )
        );

        waitForStart();
    }
}
