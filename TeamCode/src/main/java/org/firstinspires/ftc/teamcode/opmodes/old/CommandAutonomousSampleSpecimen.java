package org.firstinspires.ftc.teamcode.opmodes.old;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.ParallelDeadlineGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

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

@Disabled
@Autonomous(name = "W.M.D - Samples with Specimen", preselectTeleOp = "Command TeleOp")
public class CommandAutonomousSampleSpecimen extends CommandOpMode {
    private SubsystemsCollection sys;
    private AutoDriveUtility autoDrive;

    public void initialize() {
        SubsystemsCollection.deinit();
        sys = SubsystemsCollection.getInstance(hardwareMap);

        // -40.3, -63, rad(90)
        autoDrive = new AutoDriveUtility(hardwareMap, sys.driveBase, new Pose2d(-15.125, -63.125, Math.toRadians(180)));

        waitForStart();

        schedule(new ParallelDeadlineGroup(
                new SequentialCommandGroup(
                        //<editor-fold desc="Placing preloaded sample">
                        new CommandIntakeSetArm(Intake.ArmPosition.IDLE),
                        new ParallelCommandGroup(
                                new CommandExtakeSetLift(Extake.LiftPosition.TOP_BAR),
                                new CommandExtakeSetBucket(0.8),
                                new CommandFollowTrajectories(autoDrive,
                                        autoDrive.trajectorySequenceBuilder()
                                                .lineToConstantHeading(new Vector2d(-14, -33))
                                )
                        ),
                        new CommandExtakeSetLift(Extake.LiftPosition.DOWN),
                        //</editor-fold>

                        //<editor-fold desc="Traveling to and placing first sample from ground">
                        new ParallelCommandGroup(
                                new CommandExtakeSetBucket(Extake.BucketPosition.LOAD),
                                // new CommandExtakeSetLift(Extake.LiftPosition.DOWN),
                                // new CommandIntakeSetArm(Intake.ArmPosition.HOVER),
                                new CommandIntakeSetPivot(0.35),
                                new CommandFollowTrajectories(autoDrive,
                                        autoDrive.trajectorySequenceBuilder()
                                                .splineToLinearHeading(new Pose2d(-35.8, -37.4, Math.toRadians(145)), 3)
                                )
                        ),
                        new ParallelCommandGroup(
                                new CommandExtakeSetLift(Extake.LiftPosition.DOWN),
                                new CommandIntakeSetArm(Intake.ArmPosition.PICKUP),
                                new CommandIntakeRotateWheels(Intake.WheelPower.LOAD, 500)
                        ),
                        new CommandIntakeSetPivot(Intake.PivotPosition.MIDDLE),
                        new CommandIntakeSetArm(Intake.ArmPosition.UNLOAD),
                        new CommandIntakeRotateWheels(Intake.WheelPower.UNLOAD, 500),
                        new ParallelCommandGroup(
                                new CommandFollowTrajectories(autoDrive,
                                        autoDrive.trajectorySequenceBuilder()
                                                .splineToLinearHeading(new Pose2d(-54, -54, Math.toRadians(45)), 10)
                                ),
                                new CommandIntakeSetArm(Intake.ArmPosition.IDLE),
                                new CommandExtakeSetLift(Extake.LiftPosition.TOP_BUCKET),
                                new CommandExtakeSetBucket(.6)
                        ),
                        new CommandExtakeSetBucket(Extake.BucketPosition.UNLOAD),
                        new CommandTimer(600),
                        /* new ParallelCommandGroup(
                                new CommandFollowTrajectories(autoDrive,
                                        autoDrive.trajectorySequenceBuilder()
                                                .lineToLinearHeading(new Pose2d(-52, -52, Math.toRadians(45)))
                                ),
                                new CommandExtakeSetBucket(Extake.BucketPosition.LOAD)
                        ), */
                        //</editor-fold>

                        //<editor-fold desc="Traveling to and placing second sample from ground">
                        new ParallelCommandGroup(
                                new CommandFollowTrajectories(autoDrive,
                                        autoDrive.trajectorySequenceBuilder()
                                                .lineToLinearHeading(new Pose2d(-56, -38, Math.toRadians(90)))
                                ),
                                new CommandExtakeSetBucket(Extake.BucketPosition.LOAD),
                                // new CommandExtakeSetLift(Extake.LiftPosition.DOWN),
                                // new CommandIntakeSetArm(Intake.ArmPosition.HOVER),
                                new CommandIntakeSetPivot(Intake.PivotPosition.RIGHT)
                        ),
                        new ParallelCommandGroup(
                                new CommandExtakeSetLift(Extake.LiftPosition.DOWN),
                                new CommandIntakeSetArm(Intake.ArmPosition.PICKUP),
                                new CommandIntakeRotateWheels(Intake.WheelPower.LOAD, 500)
                        ),
                        new CommandIntakeSetPivot(Intake.PivotPosition.MIDDLE),
                        new CommandIntakeSetArm(Intake.ArmPosition.UNLOAD),
                        new CommandIntakeRotateWheels(Intake.WheelPower.UNLOAD, 500),
                        new ParallelCommandGroup(
                                new CommandFollowTrajectories(autoDrive,
                                        autoDrive.trajectorySequenceBuilder()
                                                .splineToLinearHeading(new Pose2d(-54, -54, Math.toRadians(45)), 10)
                                ),
                                new CommandIntakeSetArm(Intake.ArmPosition.IDLE),
                                new CommandExtakeSetLift(Extake.LiftPosition.TOP_BUCKET),
                                new CommandExtakeSetBucket(.6)
                        ),
                        new CommandExtakeSetBucket(Extake.BucketPosition.UNLOAD),
                        new CommandTimer(600),
                        /* new ParallelCommandGroup(
                                new CommandFollowTrajectories(autoDrive,
                                        autoDrive.trajectorySequenceBuilder()
                                                .lineToLinearHeading(new Pose2d(-52, -52, Math.toRadians(45)))
                                ),
                                new CommandExtakeSetBucket(Extake.BucketPosition.LOAD)
                        ), */
                        //</editor-fold>

                        //<editor-fold desc="Traveling to and placing third sample from ground">
                        new ParallelCommandGroup(
                                new CommandExtakeSetBucket(Extake.BucketPosition.LOAD),
                                // new CommandExtakeSetLift(Extake.LiftPosition.DOWN),
                                // new CommandIntakeSetArm(Intake.ArmPosition.HOVER),
                                new CommandFollowTrajectories(autoDrive,
                                    autoDrive.trajectorySequenceBuilder()
                                        .splineToLinearHeading(new Pose2d(-53, -26, Math.toRadians(180)), 9)
                                        //  .turn(Math.toRadians(135)
                                        //  .lineToConstantHeading(new Vector2d(-53,-26))
                                )
                        ),
                        new ParallelCommandGroup(
                                new CommandExtakeSetLift(Extake.LiftPosition.DOWN),
                                new CommandIntakeSetArm(Intake.ArmPosition.PICKUP),
                                new CommandIntakeRotateWheels(Intake.WheelPower.LOAD, 750)
                        ),
                        new CommandIntakeSetArm(Intake.ArmPosition.UNLOAD),
                        new CommandIntakeRotateWheels(Intake.WheelPower.UNLOAD, 500),
                        new ParallelCommandGroup(
                                new CommandIntakeSetArm(Intake.ArmPosition.IDLE),
                                new CommandExtakeSetLift(Extake.LiftPosition.TOP_BUCKET),
                                new CommandExtakeSetBucket(.6),
                                new CommandFollowTrajectories(autoDrive,
                                        autoDrive.trajectorySequenceBuilder()
                                                .splineToLinearHeading(new Pose2d(-54, -54, Math.toRadians(45)), 10)
                                )
                        ),
                        new CommandExtakeSetBucket(Extake.BucketPosition.UNLOAD),
                        new CommandTimer(600),

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
