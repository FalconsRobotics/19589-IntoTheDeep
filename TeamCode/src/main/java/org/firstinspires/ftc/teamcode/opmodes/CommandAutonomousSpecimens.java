package org.firstinspires.ftc.teamcode.opmodes;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.ParallelDeadlineGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.commands.CommandExtakeMoveLift;
import org.firstinspires.ftc.teamcode.commands.CommandFollowTrajectories;
import org.firstinspires.ftc.teamcode.commands.CommandIntakeRotateArm;
import org.firstinspires.ftc.teamcode.commands.CommandRun;
import org.firstinspires.ftc.teamcode.commands.CommandTimer;
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

        sys.driveBase.odometry.update();

        autoDrive = new AutoDriveUtility(hardwareMap, sys.driveBase, new Pose2d(9, -63));

        schedule(new ParallelDeadlineGroup(
                new SequentialCommandGroup(
                        new CommandIntakeRotateArm(Intake.ArmPosition.IDLE),
                        new CommandExtakeMoveLift(Extake.LiftPosition.TOP_BAR),
                        new CommandFollowTrajectories(autoDrive,
                                autoDrive.trajectorySequenceBuilder()
                                        .lineToConstantHeading(new Vector2d(1, -30))
                        ),
                        new CommandExtakeMoveLift(Extake.LiftPosition.DOWN),

                        /// Move and prepare for dragging all three pieces to human player
                        new CommandFollowTrajectories(autoDrive,
                                autoDrive.trajectorySequenceBuilder()
                                        // First piece to human player
                                        .lineToLinearHeading(new Pose2d(36, -48, Math.toRadians(90)))
                                        .splineToConstantHeading(new Pose2d(48, -8, Math.toRadians(0)).vec(), 0)
                                        .lineToConstantHeading(new Vector2d(48, -56))

                                        // Second piece to human player
                                        .lineToConstantHeading(new Vector2d(48, -24))
                                        .splineToConstantHeading(new Vector2d(58, -8), Math.toRadians(0))
                                        .lineToConstantHeading(new Vector2d(58, -56))

                                        // Third piece to human player
                                        .lineToConstantHeading(new Vector2d(58, -24))
                                        .splineToConstantHeading(new Vector2d(64, -8), Math.toRadians(0))
                                        .lineToConstantHeading(new Vector2d(64, -56))

                                        /// Move out of the way so human player gets a chance to place the first specimen.
                                        .lineToConstantHeading(new Vector2d(64, -46))
                                        .waitSeconds(1)
                                        .lineToLinearHeading(new Pose2d(43.5625, -63.625, Math.toRadians(180)))
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