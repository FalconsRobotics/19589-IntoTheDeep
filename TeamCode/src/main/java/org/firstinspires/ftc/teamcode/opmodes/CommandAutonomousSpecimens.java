package org.firstinspires.ftc.teamcode.opmodes;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.ParallelDeadlineGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.commands.CommandExtakeSetLift;
import org.firstinspires.ftc.teamcode.commands.CommandFollowTrajectories;
import org.firstinspires.ftc.teamcode.commands.CommandIntakeSetArm;
import org.firstinspires.ftc.teamcode.commands.CommandRun;
import org.firstinspires.ftc.teamcode.subsystems.Extake;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.utilities.SubsystemsCollection;
import org.firstinspires.ftc.teamcode.utilities.roadrunner.AutoDriveUtility;

@Autonomous(name = "Autonomous W.M.D. - 5specimens")
public class CommandAutonomousSpecimens extends CommandOpMode {
    private SubsystemsCollection sys;
    private AutoDriveUtility autoDrive;

    public void initialize() {
        SubsystemsCollection.deinit();
        sys = SubsystemsCollection.getInstance(hardwareMap);

        autoDrive = new AutoDriveUtility(hardwareMap, sys.driveBase, new Pose2d(9, -63, Math.toRadians(180)));

        schedule(new ParallelDeadlineGroup(
                new SequentialCommandGroup(
                        new ParallelCommandGroup(
                                new CommandIntakeSetArm(Intake.ArmPosition.IDLE),
                                new CommandExtakeSetLift(Extake.LiftPosition.TOP_BAR),
                                new CommandFollowTrajectories(autoDrive,
                                        autoDrive.trajectorySequenceBuilder()
                                                .lineToConstantHeading(new Vector2d(1, -28))
                                )
                        ),
                        new CommandExtakeSetLift(Extake.LiftPosition.DOWN),

                        /// Move and prepare for dragging all three pieces to human player
                        new CommandFollowTrajectories(autoDrive,
                                autoDrive.trajectorySequenceBuilder()
                                        // First piece to human player
                                        .strafeLeft(5)
                                        .back(24)
                                        .splineToConstantHeading(new Pose2d(48, -8, Math.toRadians(0)).vec(), 0)
                                        .lineToConstantHeading(new Vector2d(48, -56))

                                        // Second piece to human player
                                        .lineToConstantHeading(new Vector2d(48, -24))
                                        .splineToConstantHeading(new Vector2d(56, -8), Math.toRadians(0))
                                        .lineToConstantHeading(new Vector2d(56, -56))

                                        // Third piece to human player
                                        .lineToConstantHeading(new Vector2d(56, -24))
                                        .splineToConstantHeading(new Vector2d(64, -8), Math.toRadians(0))
                                        .lineToConstantHeading(new Vector2d(64, -56))

                                        /// Move out of the way so human player gets a chance to place the first specimen.
                                        .lineToConstantHeading(new Vector2d(48 - 7.75, -48))
                                        .strafeLeft(24)
                        ),

                        // First cycle
                        new ParallelCommandGroup(
                                new CommandExtakeSetLift(Extake.LiftPosition.TOP_BAR),
                                new CommandFollowTrajectories(autoDrive,
                                        autoDrive.trajectorySequenceBuilder()
                                                .lineToLinearHeading(new Pose2d(1, -30, Math.toRadians(0)))
                                )
                        ),
                        new CommandExtakeSetLift(Extake.LiftPosition.DOWN),

                        // Second Cycle
                        new CommandFollowTrajectories(autoDrive,
                                autoDrive.trajectorySequenceBuilder()
                                        .splineToConstantHeading(new Vector2d(60, -63.625), Math.toRadians(270))
                        ),
                        new ParallelCommandGroup(
                                new CommandExtakeSetLift(Extake.LiftPosition.TOP_BAR),
                                new CommandFollowTrajectories(autoDrive,
                                        autoDrive.trajectorySequenceBuilder()
                                                .lineToLinearHeading(new Pose2d(1, -30, Math.toRadians(180)))
                                )
                        ),
                        new CommandExtakeSetLift(Extake.LiftPosition.DOWN),

                        // Third Cycle
                        new CommandFollowTrajectories(autoDrive,
                                autoDrive.trajectorySequenceBuilder()
                                        .lineToConstantHeading(new Vector2d(43, -63.625))
                        ),
                        new ParallelCommandGroup(
                                new CommandExtakeSetLift(Extake.LiftPosition.TOP_BAR),
                                new CommandFollowTrajectories(autoDrive,
                                        autoDrive.trajectorySequenceBuilder()
                                                .lineToLinearHeading(new Pose2d(3, -29, Math.toRadians(0)))
                                )
                        ),
                        new CommandExtakeSetLift(Extake.LiftPosition.DOWN),

                        // Last cycle
                        new CommandFollowTrajectories(autoDrive,
                                autoDrive.trajectorySequenceBuilder()
                                        .lineToConstantHeading(new Vector2d(60, -63.625))
                        ),
                        new ParallelCommandGroup(
                                new CommandExtakeSetLift(Extake.LiftPosition.TOP_BAR),
                                new CommandFollowTrajectories(autoDrive,
                                        autoDrive.trajectorySequenceBuilder()
                                                .lineToLinearHeading(new Pose2d(2, -29, Math.toRadians(180)))
                                )
                        ),
                        new CommandExtakeSetLift(Extake.LiftPosition.DOWN),

                        // Park
                        new CommandFollowTrajectories(autoDrive,
                                autoDrive.trajectorySequenceBuilder()
                                        .lineToConstantHeading(new Vector2d(62, -63.625))
                        )
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