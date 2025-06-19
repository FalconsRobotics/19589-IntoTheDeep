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

@Autonomous(name = "CRI Red Sample", preselectTeleOp = "Command TeleOp", group = "CRI - Red")
public class CommandRedSampleAuto extends CommandOpMode {
    private SubsystemsCollection sys;
    private AutoDriveUtility autoDrive;

    public void initialize() {
        SubsystemsCollection.deinit();
        sys = SubsystemsCollection.getInstance(hardwareMap);

        int loadTimer = 450; // Timer for time it takes to suck the sample off the ground. Might not be needed?
        int spitTimer = 325; // Timer for the time it takes to spit the sample from intake to the extake bucket
        int bucketTimer = 700; // Timer for the extake to extake into top bucket.
        int driveDelay = 450; // Timer for the delay (MS) between spitting into bucket and driving to net zone
        double extakePrepareExtake = 0.7; //Bucket pos for preparing extake

        // -40.3, -63, rad(90), Math.toRadians(180)
        autoDrive = new AutoDriveUtility(hardwareMap, sys.driveBase, new Pose2d(-39.125, -63.125, Math.toRadians(180)));

        new CommandIntakeSetSlide(Intake.SlidePosition.FULLY_RETRACTED);

        waitForStart();

        schedule(new ParallelDeadlineGroup(
                new SequentialCommandGroup(
                        //<editor-fold desc="Placing preloaded sample">
                        new ParallelCommandGroup(
                                new CommandFollowTrajectories(autoDrive,
                                        autoDrive.trajectorySequenceBuilder()
                                                .lineToConstantHeading(new Vector2d(-49, -54))
                                                .lineToLinearHeading(new Pose2d(-54.25, -54.25, Math.toRadians(45)))
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
                                                .lineToLinearHeading(new Pose2d(-47, -39.3, Math.toRadians(90)))
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
                                new SequentialCommandGroup(
                                        new CommandTimer(driveDelay + 170),
                                        new CommandFollowTrajectories(autoDrive,
                                                autoDrive.trajectorySequenceBuilder()
                                                        .splineToLinearHeading(new Pose2d(-54.25, -54.25, Math.toRadians(45)), 10)
                                        )
                                ),
                                new SequentialCommandGroup(
                                        new CommandIntakeSetPivot(Intake.PivotPosition.MIDDLE),
                                        new CommandIntakeSetArm(Intake.ArmPosition.UNLOAD),
                                        new CommandIntakeRotateWheels(Intake.WheelPower.UNLOAD, spitTimer),
                                        new CommandIntakeSetArm(Intake.ArmPosition.IDLE),
                                            new ParallelCommandGroup(
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
                                                .lineToLinearHeading(new Pose2d(-56.5, -39.3, Math.toRadians(90)))
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
                                new SequentialCommandGroup(
                                        new CommandTimer(driveDelay),
                                        new CommandFollowTrajectories(autoDrive,
                                                autoDrive.trajectorySequenceBuilder()
                                                        .splineToLinearHeading(new Pose2d(-54.25, -54.25, Math.toRadians(45)), 10)
                                        )
                                ),
                                new SequentialCommandGroup(
                                        new CommandIntakeSetPivot(Intake.PivotPosition.MIDDLE),
                                        new CommandIntakeSetArm(Intake.ArmPosition.UNLOAD),
                                        new CommandIntakeRotateWheels(Intake.WheelPower.UNLOAD, spitTimer),
                                        new CommandIntakeSetArm(Intake.ArmPosition.IDLE),
                                            new ParallelCommandGroup(
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
                                                .splineToLinearHeading(new Pose2d(-52.5, -26, Math.toRadians(180)), 9)
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
                                new CommandIntakeSetArm(-40), // Hack for intaking this sample. I assume there is a tolerance issue
                                new CommandIntakeRotateWheels(Intake.WheelPower.LOAD, (loadTimer + 300)) // Cool hack! :D
                        ),
                        new ParallelCommandGroup(
                                new SequentialCommandGroup(
                                        new CommandTimer(driveDelay),
                                        new CommandFollowTrajectories(autoDrive,
                                                autoDrive.trajectorySequenceBuilder()
                                                        .splineToLinearHeading(new Pose2d(-54.25, -54.25, Math.toRadians(45)), 10)
                                        )
                                ),
                                new SequentialCommandGroup(
                                        new CommandIntakeSetPivot(Intake.PivotPosition.MIDDLE),
                                        new CommandIntakeSetArm(-1060), // Position hack because it was dropping for some reason
                                        new CommandIntakeRotateWheels(Intake.WheelPower.UNLOAD, spitTimer),
                                        new CommandIntakeSetArm(Intake.ArmPosition.IDLE),
                                        new ParallelCommandGroup(
                                                new CommandExtakeSetLift(Extake.LiftPosition.TOP_BUCKET),
                                                new CommandExtakeSetBucket(extakePrepareExtake)
                                        )
                                )
                        ),
                        new CommandExtakeSetBucket(Extake.BucketPosition.UNLOAD),
                        new CommandTimer(bucketTimer),

                        // Going to submersible and getting a 5th sample!
                        /*
                        new ParallelDeadlineGroup(
                                new CommandFollowTrajectories(autoDrive,
                                        autoDrive.trajectorySequenceBuilder()
                                                .splineToLinearHeading(new Pose2d(-16, -12, Math.toRadians(0)), 6)
                                ),
                                new CommandExtakeSetBucket(Extake.BucketPosition.LOAD),
                                new CommandIntakeSetArm(Intake.ArmPosition.HOVER),
                                new SequentialCommandGroup(
                                        new CommandTimer(200),
                                        new CommandExtakeSetLift(Extake.LiftPosition.DOWN)
                                )
                        ),

                        new CommandLimelightStatus(hardwareMap, CommandLimelightStatus.LimelightStatus.Start),

                        new ParallelCommandGroup(
                                new CommandIntakeAutoSlide(hardwareMap),
                                new CommandIntakeAutoPivot(hardwareMap),
                                new CommandAutoStrafe(hardwareMap)
                        ),

                        new ParallelDeadlineGroup(
                                new CommandIntakeRotateWheels(Intake.WheelPower.LOAD, loadTimer),
                                new CommandIntakeSetArm(Intake.ArmPosition.PICKUP)
                        ),

                        new CommandLimelightStatus(hardwareMap, CommandLimelightStatus.LimelightStatus.Stop),

                        new ParallelCommandGroup(
                                new SequentialCommandGroup(
                                        new CommandFollowTrajectories(autoDrive,
                                                autoDrive.trajectorySequenceBuilder()
                                                        .back(20)
                                                        .lineToLinearHeading(new Pose2d(-54.25, -54.25, Math.toRadians(45)))
                                        )
                                ),
                                new SequentialCommandGroup(
                                        new CommandIntakeSetPivot(Intake.PivotPosition.MIDDLE),
                                        new CommandIntakeSetArm(-1060), // Position hack because it was dropping for some reason
                                        new CommandIntakeRotateWheels(Intake.WheelPower.UNLOAD, spitTimer),
                                        new CommandIntakeSetArm(Intake.ArmPosition.IDLE),
                                        new ParallelCommandGroup(
                                                new CommandExtakeSetLift(Extake.LiftPosition.TOP_BUCKET),
                                                new CommandExtakeSetBucket(extakePrepareExtake)
                                        )
                                )
                        ),
                        new CommandExtakeSetBucket(Extake.BucketPosition.UNLOAD),
                        new CommandTimer(bucketTimer)
                        */
                        /// Level 1 ascent!!!
                        /*new CommandExtakeSetBucket(Extake.BucketPosition.LOAD),
                        new CommandFollowTrajectories(autoDrive,
                                autoDrive.trajectorySequenceBuilder()
                                        .lineToLinearHeading(new Pose2d(-40, -8, Math.toRadians(90)))
                        ),
                        new ParallelDeadlineGroup(
                                new CommandFollowTrajectories(autoDrive,
                                        autoDrive.trajectorySequenceBuilder()
                                                .lineToLinearHeading(new Pose2d(-17.5, -8, Math.toRadians(90)))
                                ),
                                new CommandIntakeSetArm(-1000),
                                new CommandExtakeSetLift(390)
                        ), */

                        new CommandFollowTrajectories(autoDrive, autoDrive.trajectorySequenceBuilder().forward(3)),

                        new CommandRun(() -> {
                            sys.extake.lift.motor.set(-0.1);
                            requestOpModeStop();
                            return true; // finish!!!!!!!!!
                        })
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
