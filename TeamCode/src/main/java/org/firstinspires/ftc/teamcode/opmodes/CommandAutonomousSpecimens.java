package org.firstinspires.ftc.teamcode.opmodes;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.ParallelDeadlineGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.Subsystem;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.commands.CommandExtakeSetLift;
import org.firstinspires.ftc.teamcode.commands.CommandFollowTrajectories;
import org.firstinspires.ftc.teamcode.commands.CommandIntakeSetArm;
import org.firstinspires.ftc.teamcode.commands.CommandIntakeSetPivot;
import org.firstinspires.ftc.teamcode.commands.CommandRun;
import org.firstinspires.ftc.teamcode.subsystems.Extake;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.utilities.SubsystemsCollection;
import org.firstinspires.ftc.teamcode.utilities.roadrunner.AutoDriveUtility;

import java.util.Collections;
import java.util.Set;

@Autonomous(name = "Autonomous W.M.D. - 5specimens")
public class CommandAutonomousSpecimens extends CommandOpMode {
    private SubsystemsCollection sys;
    private AutoDriveUtility autoDrive;

    public void initialize() {
        SubsystemsCollection.deinit();
        sys = SubsystemsCollection.getInstance(hardwareMap);

        autoDrive = new AutoDriveUtility(hardwareMap, sys.driveBase, new Pose2d(9, -63, Math.toRadians(180)));

        waitForStart();

        schedule(new ParallelDeadlineGroup(
                new SequentialCommandGroup(
                        new ParallelCommandGroup(
                                new CommandFollowTrajectories(autoDrive,
                                        autoDrive.trajectorySequenceBuilder()
                                                .lineToConstantHeading(new Vector2d(0, -30))
                                ),
                                new CommandExtakeSetLift(Extake.LiftPosition.TOP_BAR),
                                new CommandIntakeSetPivot(Intake.ArmPosition.IDLE)
                        ),
                        new CommandExtakeSetLift(Extake.LiftPosition.DOWN),
                        new CommandFollowTrajectories(autoDrive,
                                autoDrive.trajectorySequenceBuilder()
                                        .back(20)
                                        .splineToConstantHeading(new Pose2d(48, -8, Math.toRadians(180)).vec(), 0)
                                        .lineToConstantHeading(new Vector2d(48, -56))

                                        .lineToConstantHeading(new Vector2d(48, -24))
                                        .splineToConstantHeading(new Vector2d(58, -8), Math.toRadians(0))
                                        .strafeLeft(32)
                                        .splineToLinearHeading(new Pose2d(44, -62.625, Math.toRadians(180)), Math.toRadians(270))
                        ),
                        new CommandExtakeSetLift(Extake.LiftPosition.DOWN),
                        new ParallelCommandGroup(
                                new CommandFollowTrajectories(autoDrive,
                                        autoDrive.trajectorySequenceBuilder()
                                                .splineToSplineHeading(new Pose2d(-1, -30, Math.toRadians(0)), Math.toRadians(90 - 1e+6))
                                ),
                                new CommandExtakeSetLift(Extake.LiftPosition.TOP_BAR)
                        ),
                        new CommandExtakeSetLift(Extake.LiftPosition.DOWN),
                        new CommandFollowTrajectories(autoDrive,
                                autoDrive.trajectorySequenceBuilder()
                                        .splineToConstantHeading(new Vector2d(60, -62.625), Math.toRadians(270)) // Same deal with the positioning. Facing right it should be around 60.
                        ),
                        new ParallelCommandGroup(
                                new CommandFollowTrajectories(autoDrive,
                                        autoDrive.trajectorySequenceBuilder()
                                                .splineToLinearHeading(new Pose2d(2, -30, Math.toRadians(180)), Math.toRadians(90 - 1e+6))
                                ),
                                new CommandExtakeSetLift(Extake.LiftPosition.TOP_BAR)
                        ),
                        new CommandExtakeSetLift(Extake.LiftPosition.DOWN),

                        /// Park
                        new CommandFollowTrajectories(autoDrive,
                                autoDrive.trajectorySequenceBuilder()
                                        .lineToConstantHeading(new Vector2d(48, -48))
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