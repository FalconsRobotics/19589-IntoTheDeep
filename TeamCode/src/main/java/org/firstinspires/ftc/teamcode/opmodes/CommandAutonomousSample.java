package org.firstinspires.ftc.teamcode.opmodes;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.ParallelDeadlineGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.internal.system.Deadline;
import org.firstinspires.ftc.teamcode.commands.CommandExtakeSetBucket;
import org.firstinspires.ftc.teamcode.commands.CommandExtakeSetLift;
import org.firstinspires.ftc.teamcode.commands.CommandFollowTrajectories;
import org.firstinspires.ftc.teamcode.commands.CommandIntakeRotateWheels;
import org.firstinspires.ftc.teamcode.commands.CommandIntakeSetArm;
import org.firstinspires.ftc.teamcode.commands.CommandIntakeSetPivot;
import org.firstinspires.ftc.teamcode.commands.CommandRun;
import org.firstinspires.ftc.teamcode.commands.CommandTimer;
import org.firstinspires.ftc.teamcode.subsystems.Extake;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.utilities.SubsystemsCollection;
import org.firstinspires.ftc.teamcode.utilities.roadrunner.AutoDriveUtility;

@Autonomous(name = "Auto W.M.D - 4Samples", preselectTeleOp = "Command TeleOp")
public class CommandAutonomousSample extends CommandOpMode {
    private SubsystemsCollection sys;
    private AutoDriveUtility autoDrive;

    public void initialize() {
        SubsystemsCollection.deinit();
        sys = SubsystemsCollection.getInstance(hardwareMap);

        int loadTimer = 100; //Timer for time it takes to suck the sample off the ground. Might not be needed?
        int spitTimer = 325; //Timer for the time it takes to spit the sample from intake to the extake bucket
        int bucketTimer = 450; //Timer for the extake to extake into top bucket.
        double extakePrepareExtake = 0.55; //Bucket pos for preparing extake

        // -40.3, -63, rad(90), Math.toRadians(180)
        autoDrive = new AutoDriveUtility(hardwareMap, sys.driveBase, new Pose2d(-39.125, -63.125, Math.toRadians(180)));

        waitForStart();

        schedule(new ParallelDeadlineGroup(
                new SequentialCommandGroup(
                        //<editor-fold desc="Placing preloaded sample">
                        new ParallelCommandGroup(
                                new CommandFollowTrajectories(autoDrive,
                                        autoDrive.trajectorySequenceBuilder()
                                                .lineToConstantHeading(new Vector2d(-49, -54))
                                                .lineToLinearHeading(new Pose2d(-54, -54, Math.toRadians(45)))
                                ),
                                    new SequentialCommandGroup(
                                            new CommandIntakeSetArm(Intake.ArmPosition.IDLE),
                                            new CommandExtakeSetBucket(Extake.BucketPosition.PREPARE_UNLOAD),
                                            new CommandExtakeSetLift(Extake.LiftPosition.TOP_BUCKET),
                                            new CommandExtakeSetBucket(extakePrepareExtake)
                                    )
                        ),
                        new CommandExtakeSetBucket(Extake.BucketPosition.UNLOAD),
                        new CommandTimer(bucketTimer),
                        //</editor-fold>

                        //<editor-fold desc="Traveling to and placing first sample from ground">
                        new ParallelDeadlineGroup(
                                new CommandFollowTrajectories(autoDrive,
                                        autoDrive.trajectorySequenceBuilder()
                                                .lineToLinearHeading(new Pose2d(-47, -38.75, Math.toRadians(90)))
                                ),
                                new CommandExtakeSetBucket(Extake.BucketPosition.LOAD),
                                new CommandIntakeSetArm(Intake.ArmPosition.HOVER),
                                new CommandIntakeSetPivot(Intake.PivotPosition.RIGHT),
                                    new SequentialCommandGroup(
                                            new CommandTimer(200),
                                            new CommandExtakeSetLift(Extake.LiftPosition.DOWN)
                                    )
                        ),
                        new ParallelCommandGroup(
                                new CommandExtakeSetLift(Extake.LiftPosition.DOWN),
                                new CommandIntakeSetArm(Intake.ArmPosition.PICKUP),
                                new CommandIntakeRotateWheels(Intake.WheelPower.LOAD, loadTimer)
                        ),
                        new ParallelCommandGroup(
                                new CommandFollowTrajectories(autoDrive,
                                        autoDrive.trajectorySequenceBuilder()
                                                .splineToLinearHeading(new Pose2d(-53, -53, Math.toRadians(45)), 10)
                                ),
                                new SequentialCommandGroup(
                                        new CommandIntakeSetPivot(Intake.PivotPosition.MIDDLE),
                                        new CommandIntakeSetArm(Intake.ArmPosition.UNLOAD),
                                        new CommandIntakeRotateWheels(Intake.WheelPower.UNLOAD, spitTimer),
                                            new ParallelCommandGroup(
                                                    new CommandIntakeSetArm(Intake.ArmPosition.IDLE),
                                                    new CommandExtakeSetLift(Extake.LiftPosition.TOP_BUCKET),
                                                    new CommandExtakeSetBucket(extakePrepareExtake)
                                            )
                                )
                        ),
                        new CommandExtakeSetBucket(Extake.BucketPosition.UNLOAD),
                        new CommandTimer(bucketTimer),
                        //</editor-fold>

                        //<editor-fold desc="Traveling to and placing second sample from ground">
                        new ParallelDeadlineGroup(
                                new CommandFollowTrajectories(autoDrive,
                                        autoDrive.trajectorySequenceBuilder()
                                                .lineToLinearHeading(new Pose2d(-56.5, -38.75, Math.toRadians(90)))
                                ),
                                new CommandExtakeSetBucket(Extake.BucketPosition.LOAD),
                                new CommandIntakeSetArm(Intake.ArmPosition.HOVER),
                                new CommandIntakeSetPivot(Intake.PivotPosition.RIGHT),
                                    new SequentialCommandGroup(
                                        new CommandTimer(200),
                                        new CommandExtakeSetLift(Extake.LiftPosition.DOWN)
                                    )
                        ),
                        new ParallelCommandGroup(
                                new CommandExtakeSetLift(Extake.LiftPosition.DOWN),
                                new CommandIntakeSetArm(Intake.ArmPosition.PICKUP),
                                new CommandIntakeRotateWheels(Intake.WheelPower.LOAD, loadTimer)
                        ),
                        new ParallelCommandGroup(
                                new CommandFollowTrajectories(autoDrive,
                                        autoDrive.trajectorySequenceBuilder()
                                                .splineToLinearHeading(new Pose2d(-54, -54, Math.toRadians(45)), 10)
                                ),
                                new SequentialCommandGroup(
                                        new CommandIntakeSetPivot(Intake.PivotPosition.MIDDLE),
                                        new CommandIntakeSetArm(Intake.ArmPosition.UNLOAD),
                                        new CommandIntakeRotateWheels(Intake.WheelPower.UNLOAD, spitTimer),
                                            new ParallelCommandGroup(
                                                    new CommandIntakeSetArm(Intake.ArmPosition.IDLE),
                                                    new CommandExtakeSetLift(Extake.LiftPosition.TOP_BUCKET),
                                                    new CommandExtakeSetBucket(extakePrepareExtake)
                                            )
                                )
                        ),
                        new CommandExtakeSetBucket(Extake.BucketPosition.UNLOAD),
                        new CommandTimer(bucketTimer),
                        //</editor-fold>

                        //<editor-fold desc="Traveling to and placing third sample from ground">
                        new ParallelDeadlineGroup(
                                new CommandFollowTrajectories(autoDrive,
                                        autoDrive.trajectorySequenceBuilder()
                                                .splineToLinearHeading(new Pose2d(-53, -26, Math.toRadians(180)), 9)
                                ),
                                new CommandExtakeSetBucket(Extake.BucketPosition.LOAD),
                                new CommandIntakeSetArm(Intake.ArmPosition.HOVER),
                                    new SequentialCommandGroup(
                                            new CommandTimer(200),
                                            new CommandExtakeSetLift(Extake.LiftPosition.DOWN)
                                    )
                        ),
                        new ParallelCommandGroup(
                                new CommandExtakeSetLift(Extake.LiftPosition.DOWN),
                                new CommandIntakeSetArm(Intake.ArmPosition.PICKUP),
                                new CommandIntakeRotateWheels(Intake.WheelPower.LOAD, (loadTimer + 150))
                        ),
                        new ParallelCommandGroup(
                                new CommandFollowTrajectories(autoDrive,
                                        autoDrive.trajectorySequenceBuilder()
                                                .splineToLinearHeading(new Pose2d(-54, -54, Math.toRadians(45)), 10)
                                ),
                                new SequentialCommandGroup(
                                        new CommandIntakeSetPivot(Intake.PivotPosition.MIDDLE),
                                        new CommandIntakeSetArm(-1060), // Position hack because it was dropping for some reason
                                        new CommandIntakeRotateWheels(Intake.WheelPower.UNLOAD, spitTimer),
                                        new ParallelCommandGroup(
                                                new CommandIntakeSetArm(Intake.ArmPosition.IDLE),
                                                new CommandExtakeSetLift(Extake.LiftPosition.TOP_BUCKET),
                                                new CommandExtakeSetBucket(extakePrepareExtake)
                                        )
                                )
                        ),
                        new CommandExtakeSetBucket(Extake.BucketPosition.UNLOAD),
                        new CommandTimer(bucketTimer),

                        /// Temporary: Final parking
                        new ParallelCommandGroup(
                                new CommandFollowTrajectories(autoDrive,
                                    autoDrive.trajectorySequenceBuilder()
                                        .forward(5)
                                ),
                                new CommandExtakeSetBucket(Extake.BucketPosition.LOAD)
                        )
                        //</editor-fold>
                ),

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
