package org.firstinspires.ftc.teamcode.opmodes.disabled;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.ParallelDeadlineGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.commands.CommandExtakeSetLift;
import org.firstinspires.ftc.teamcode.commands.CommandFollowTrajectories;
import org.firstinspires.ftc.teamcode.commands.CommandIntakeSetPivot;
import org.firstinspires.ftc.teamcode.commands.CommandRun;
import org.firstinspires.ftc.teamcode.commands.CommandTimer;
import org.firstinspires.ftc.teamcode.subsystems.Extake;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.utilities.SubsystemsCollection;
import org.firstinspires.ftc.teamcode.utilities.roadrunner.AutoDriveUtility;

@Disabled
@Autonomous(name = "Autonomous W.M.D. - Three Specimens")
public class CommandAutonomousSpecimens extends CommandOpMode {
    private SubsystemsCollection sys;
    private AutoDriveUtility autoDrive;

    public void initialize() {
        SubsystemsCollection.deinit();
        sys = SubsystemsCollection.getInstance(hardwareMap);

        autoDrive = new AutoDriveUtility(hardwareMap, sys.driveBase, new Pose2d(9, -63, Math.toRadians(180)));

        waitForStart();

        schedule(new ParallelDeadlineGroup(
                new CommandTimer(30000),
                new ParallelDeadlineGroup(
                        new SequentialCommandGroup(
                                new ParallelCommandGroup(
                                        new CommandFollowTrajectories(autoDrive,
                                                autoDrive.trajectorySequenceBuilder()
                                                        .lineToConstantHeading(new Vector2d(0, -29))
                                        ),
                                        new CommandExtakeSetLift(Extake.LiftPosition.TOP_BAR),
                                        new CommandIntakeSetPivot(Intake.ArmPosition.IDLE)
                                ),
                                new ParallelCommandGroup(
                                        new CommandFollowTrajectories(autoDrive,
                                                autoDrive.trajectorySequenceBuilder()
                                                        .strafeLeft(4)
                                        ),
                                        new CommandExtakeSetLift(Extake.LiftPosition.DOWN)
                                ),
                                new CommandFollowTrajectories(autoDrive,
                                        autoDrive.trajectorySequenceBuilder()
                                                .back(23)
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
        //                                              .splineToLinearHeading(new Pose2d(-1, -30, Math.toRadians(0)), Math.toRadians(90 - 1e+6))
                                                        .lineToLinearHeading(new Pose2d(5, -29, Math.toRadians(0)))
                                        ),
                                        new CommandExtakeSetLift(Extake.LiftPosition.TOP_BAR)
                                ),
                                new CommandExtakeSetLift(Extake.LiftPosition.DOWN),
                                new CommandFollowTrajectories(autoDrive,
                                        autoDrive.trajectorySequenceBuilder()
                                                .splineToConstantHeading(new Vector2d(59, -63), Math.toRadians(270))
                                ),
                                new ParallelCommandGroup(
                                        new CommandFollowTrajectories(autoDrive,
                                                autoDrive.trajectorySequenceBuilder()
        //                                              .splineToLinearHeading(new Pose2d(2, -30, Math.toRadians(180)), Math.toRadians(90 - 1e+6))
                                                        .lineToLinearHeading(new Pose2d(2, -30, Math.toRadians(180)))
                                        ),
                                        new CommandExtakeSetLift(Extake.LiftPosition.TOP_BAR)
                                ),
                                new CommandExtakeSetLift(Extake.LiftPosition.DOWN),

                                /// Park by collecting the next specimen
                                new CommandFollowTrajectories(autoDrive,
                                        autoDrive.trajectorySequenceBuilder()
                                                .splineToConstantHeading(new Vector2d(41, -62.625), Math.toRadians(270))
                                )
                        ),

                        new CommandRun(() -> {
                            if (isStopRequested()) {
                                autoDrive.abort();
                            }

                            sys.intake.pivot.servo.setPosition(Intake.PivotPosition.MIDDLE);

                            autoDrive.printPoseEstimate(telemetry);
                            telemetry.addData("Odo X", sys.driveBase.odometry.getPosX());
                            telemetry.addData("Odo Y", sys.driveBase.odometry.getPosY());
                            telemetry.update();

                            return false; // never finish.
                        })
                )
        ));

        telemetry.addLine("Build Complete");
        telemetry.update();
    }


}