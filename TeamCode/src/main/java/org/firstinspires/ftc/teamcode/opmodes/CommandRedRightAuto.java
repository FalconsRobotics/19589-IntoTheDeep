package org.firstinspires.ftc.teamcode.opmodes;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
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

@Config
@Autonomous(name = "CRI Red Right Side", preselectTeleOp = "Command TeleOp", group = "CRI - Red")
public class CommandRedRightAuto extends CommandTeleOp {
    private SubsystemsCollection sys;
    private AutoDriveUtility autoDrive;

    public void initialize() {
        SubsystemsCollection.deinit();
        sys = SubsystemsCollection.getInstance(hardwareMap);

        // Starting positions for the robot.
        // Robot is 13 x 17.5 so adding the halves allows the robot to be started on the edge of the tile.
        double startingX = 72 + (17.5 / 2);
        double startingY = -72 + (double)(13 / 2);

        // Attempts at storing reusable positions inside premade Pose2d variables to make code cleaner
        // and make it easier to change positions.
        Pose2d intermediateBarPosition = new Pose2d(startingX + 10, startingY + 23.75, Math.toRadians(270));
        Pose2d barPosition = new Pose2d(startingX - 10, startingY + 47.5, Math.toRadians(270));

        autoDrive = new AutoDriveUtility(hardwareMap, sys.driveBase, new Pose2d(startingX, startingY, Math.toRadians(180)));

        waitForStart();

        schedule(new ParallelDeadlineGroup(
                new SequentialCommandGroup(
                    new ParallelCommandGroup(
                            new CommandFollowTrajectories(autoDrive,
                                    autoDrive.trajectorySequenceBuilder()
                                            .lineToLinearHeading(intermediateBarPosition)
                                            .splineToConstantHeading(barPosition.vec(), Math.toRadians(315))
                            ),
                            new CommandIntakeSetArm(Intake.ArmPosition.IDLE),
                            new CommandExtakeSetLift(Extake.LiftPosition.TOP_BAR - 100)
                    ),
                    new CommandExtakeSetLift(Extake.LiftPosition.DOWN)
                ),
                new CommandRun(() -> {
                    if (isStopRequested()) autoDrive.abort();

                    autoDrive.printPoseEstimate(telemetry);
                    telemetry.addData("Odo X", sys.driveBase.odometry.getPosX());
                    telemetry.addData("Odo Y", sys.driveBase.odometry.getPosY());
                    telemetry.update();

                    return false;
                })
        ));
    }
}