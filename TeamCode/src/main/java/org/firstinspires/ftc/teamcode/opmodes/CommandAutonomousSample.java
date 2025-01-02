package org.firstinspires.ftc.teamcode.opmodes;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.commands.CommandExtakeMoveLift;
import org.firstinspires.ftc.teamcode.commands.CommandExtakeRotateArm;
import org.firstinspires.ftc.teamcode.commands.CommandFollowTrajectories;
import org.firstinspires.ftc.teamcode.commands.CommandIntakeRotateArm;
import org.firstinspires.ftc.teamcode.commands.CommandIntakeRotateWheels;
import org.firstinspires.ftc.teamcode.commands.CommandRun;
import org.firstinspires.ftc.teamcode.commands.CommandTimer;
import org.firstinspires.ftc.teamcode.subsystems.Extake;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.utilities.SubsystemsCollection;
import org.firstinspires.ftc.teamcode.utilities.roadrunner.AutoDriveUtility;

@Autonomous(name = "")
public class CommandAutonomousSample extends CommandOpMode {
    private SubsystemsCollection sys;
    private AutoDriveUtility autoDrive;

    public void initialize() {
        SubsystemsCollection.deinit();
        sys = SubsystemsCollection.getInstance(hardwareMap);

        autoDrive = new AutoDriveUtility(hardwareMap, sys.driveBase, new Pose2d(0, 0));

        SequentialCommandGroup goToBucketAndUnload = new SequentialCommandGroup(
                // Use directly after intakeSample.
                new CommandIntakeRotateArm(Intake.ArmPosition.IDLE),
                new ParallelCommandGroup(
                        new CommandExtakeMoveLift(Extake.LiftPosition.TOP_BUCKET),
                        new CommandExtakeRotateArm(Extake.ArmPosition.PREPARE_UNLOAD)
                ),
                new CommandFollowTrajectories(autoDrive,
                        autoDrive.trajectorySequenceBuilder()
                                .splineToLinearHeading(new Pose2d(1.5, 19.5, Math.toRadians(-45)), 0)
                ),
                new CommandExtakeRotateArm(Extake.ArmPosition.UNLOAD),
                new CommandTimer(350),
                new ParallelCommandGroup(
                        new CommandExtakeRotateArm(Extake.ArmPosition.LOAD),
                        new CommandExtakeMoveLift(Extake.LiftPosition.DOWN)
                )
        );

        SequentialCommandGroup intakeSample = new SequentialCommandGroup(
                new ParallelCommandGroup(
                        new CommandIntakeRotateArm(Intake.ArmPosition.PICKUP),
                        new CommandIntakeRotateWheels(.85, 800)
                ),
                new CommandIntakeRotateArm(Intake.ArmPosition.UNLOAD),
                new CommandIntakeRotateWheels(-.45, 500)
        );


        schedule(new ParallelCommandGroup(
                new SequentialCommandGroup(
                        new CommandFollowTrajectories(autoDrive,
                                autoDrive.trajectorySequenceBuilder()
                                        .forward(19)
                        ),
                        goToBucketAndUnload,
                        new CommandFollowTrajectories(autoDrive,
                                autoDrive.trajectorySequenceBuilder()
                                        .splineToLinearHeading(new Pose2d(18.5, 11.5, 0), 0)
                        ),
                        intakeSample,
                        goToBucketAndUnload,
                        new CommandFollowTrajectories(autoDrive,
                                autoDrive.trajectorySequenceBuilder()
                                        .splineToLinearHeading(new Pose2d(18.5, 21.5, 0), 0)
                        ),
                        intakeSample,
                        goToBucketAndUnload,
                        new CommandFollowTrajectories(autoDrive,
                                autoDrive.trajectorySequenceBuilder()
                                        .splineToLinearHeading(new Pose2d(18.5, 11.5, 0), 0)
                        )
                        // TODO: Line up to grab sample 4. Then intake and gotobucket. Cool!
                ),

                new CommandRun(() -> {
                    autoDrive.printPoseEstimate(telemetry);
                    telemetry.addData("Odo X", sys.driveBase.odometry.getPosX());
                    telemetry.addData("Odo Y", sys.driveBase.odometry.getPosY());
                    telemetry.update();

                    return false; // never finish.
                })
        ));
    }
}
