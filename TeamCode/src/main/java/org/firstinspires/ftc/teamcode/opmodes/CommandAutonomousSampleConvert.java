package org.firstinspires.ftc.teamcode.opmodes;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.ParallelDeadlineGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.commands.CommandExtakeSetBucket;
import org.firstinspires.ftc.teamcode.commands.CommandExtakeSetLift;
import org.firstinspires.ftc.teamcode.commands.CommandFollowTrajectories;
import org.firstinspires.ftc.teamcode.commands.CommandIntakeRotateWheels;
import org.firstinspires.ftc.teamcode.commands.CommandIntakeSetArm;
import org.firstinspires.ftc.teamcode.commands.CommandIntakeSetPivot;
import org.firstinspires.ftc.teamcode.commands.CommandIntakeSetSlide;
import org.firstinspires.ftc.teamcode.commands.CommandRun;
import org.firstinspires.ftc.teamcode.commands.CommandTimer;
import org.firstinspires.ftc.teamcode.subsystems.Extake;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.utilities.SubsystemsCollection;
import org.firstinspires.ftc.teamcode.utilities.roadrunner.AutoDriveUtility;

@Autonomous(name = "Autonomous W.M.D - 4Samples Convert", preselectTeleOp = "Command TeleOp")
public class CommandAutonomousSampleConvert extends CommandOpMode {
    private SubsystemsCollection sys;
    private AutoDriveUtility autoDrive;


    public void initialize() {
        SubsystemsCollection.deinit();
        sys = SubsystemsCollection.getInstance(hardwareMap);

        int slideTimer = 250; /// Millisecond delay from intake to putting the sample into the bucket. Slides need time to move

        // -40.3, -63, rad(90), Math.toRadians(180)
        autoDrive = new AutoDriveUtility(hardwareMap, sys.driveBase, new Pose2d(-39.125, -63.125, Math.toRadians(180)));

        waitForStart();

        schedule(new ParallelDeadlineGroup(
                new SequentialCommandGroup(
                        new CommandIntakeSetArm(Intake.ArmPosition.IDLE),
                        new ParallelCommandGroup(
                                new CommandExtakeSetLift(Extake.LiftPosition.TOP_BUCKET),
                                new CommandExtakeSetBucket(Extake.BucketPosition.PREPARE_UNLOAD),
                                new CommandFollowTrajectories(autoDrive,
                                        autoDrive.trajectorySequenceBuilder()
                                                .lineToConstantHeading(new Vector2d(-42, -54))
                                                .lineToLinearHeading(new Pose2d(-54, -54, Math.toRadians(45)))
                                )
                        ),
                        new CommandExtakeSetBucket(Extake.BucketPosition.UNLOAD),
                        new CommandTimer(250),

                        new ParallelCommandGroup(
                                new CommandIntakeSetArm(Intake.ArmPosition.HOVER),
                                new CommandIntakeSetPivot(Intake.PivotPosition.RIGHT),
                                new CommandIntakeSetSlide(Intake.SlidePosition.EXTENDED),
                                new CommandFollowTrajectories(autoDrive,
                                        autoDrive.trajectorySequenceBuilder()
                                                .lineToLinearHeading(new Pose2d(-54, -44, Math.toRadians(70)))
                        ),
                        new CommandIntakeSetArm(Intake.ArmPosition.PICKUP),
                        new ParallelCommandGroup(
                                new CommandIntakeSetPivot(Intake.PivotPosition.MIDDLE),
                                new CommandIntakeSetArm(Intake.ArmPosition.UNLOAD),
                                new CommandIntakeSetSlide(Intake.SlidePosition.RETRACTED),
                                new CommandFollowTrajectories(autoDrive,
                                        autoDrive.trajectorySequenceBuilder()
                                                .lineToLinearHeading(new Pose2d(-54, -54, Math.toRadians(45))))
                        ),
                        new ParallelCommandGroup(
                                new CommandExtakeSetLift(Extake.LiftPosition.TOP_BUCKET),
                                new CommandExtakeSetBucket(Extake.BucketPosition.PREPARE_UNLOAD)
                        ),
                        new CommandExtakeSetBucket(Extake.BucketPosition.UNLOAD)
                )),

                new CommandRun(() -> {
                    if (isStopRequested()) {
                        autoDrive.abort();
                    }

                    autoDrive.printPoseEstimate(telemetry);
                    telemetry.addData("Odo X", sys.driveBase.odometry.getPosX());
                    telemetry.addData("Odo Y", sys.driveBase.odometry.getPosY());
                    telemetry.update();

                    return false; // never finish.
                })
        ));

        telemetry.addLine("Build Complete");
        telemetry.update();
    }
}
