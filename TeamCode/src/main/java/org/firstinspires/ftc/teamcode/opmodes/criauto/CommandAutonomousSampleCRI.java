package org.firstinspires.ftc.teamcode.opmodes.criauto;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.ParallelDeadlineGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.commands.CommandExtakeSetBucket;
import org.firstinspires.ftc.teamcode.commands.CommandExtakeSetLift;
import org.firstinspires.ftc.teamcode.commands.CommandFollowTrajectories;
import org.firstinspires.ftc.teamcode.commands.CommandIntakeSetArm;
import org.firstinspires.ftc.teamcode.commands.CommandRun;
import org.firstinspires.ftc.teamcode.subsystems.Extake;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.utilities.SubsystemsCollection;
import org.firstinspires.ftc.teamcode.utilities.roadrunner.AutoDriveUtility;

@Autonomous(name = "Autonomous - CRI Sample / Full Left")
public class CommandAutonomousSampleCRI extends CommandOpMode {
    private SubsystemsCollection sys;
    private AutoDriveUtility autoDrive;

    public void initialize() {
        SubsystemsCollection.deinit();
        sys = SubsystemsCollection.getInstance(hardwareMap);

        autoDrive = new AutoDriveUtility(hardwareMap, sys.driveBase, new Pose2d(-48 + (17.5 / 2), -72 + ((double) 13 / 2), Math.toRadians(0)));

        waitForStart();

        schedule(new ParallelDeadlineGroup(
                new SequentialCommandGroup(
                        new CommandIntakeSetArm(Intake.ArmPosition.IDLE),
                        new CommandExtakeSetLift(Extake.LiftPosition.DOWN),
                        new CommandFollowTrajectories(autoDrive,
                                autoDrive.trajectorySequenceBuilder()
                                    .splineToLinearHeading(new Pose2d(-60, -60, Math.toRadians(315)),  Math.toRadians(315))
                        ),
                        new CommandExtakeSetLift(Extake.LiftPosition.TOP_BUCKET),
                        new CommandExtakeSetBucket(Extake.BucketPosition.UNLOAD)
                ),
                new CommandRun(() -> {
                    if (isStopRequested()) autoDrive.abort();

                    autoDrive.printPoseEstimate(telemetry);
                    telemetry.update();
                    return false;
                })
        ));
    }
}