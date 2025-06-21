package org.firstinspires.ftc.teamcode.opmodes;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.ParallelDeadlineGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.commands.CommandExtakeSetLift;
import org.firstinspires.ftc.teamcode.commands.CommandFollowTrajectories;
import org.firstinspires.ftc.teamcode.commands.CommandIntakeSetSlide;
import org.firstinspires.ftc.teamcode.commands.CommandRun;
import org.firstinspires.ftc.teamcode.subsystems.Extake;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.utilities.SubsystemsCollection;
import org.firstinspires.ftc.teamcode.utilities.roadrunner.AutoDriveUtility;

@Autonomous(name = "CRI Specimen", preselectTeleOp = "Command TeleOp")
public class CommandSpecimenAuto extends CommandOpMode {
    private SubsystemsCollection sys;
    private AutoDriveUtility autoDrive;

    public void initialize() {
        SubsystemsCollection.deinit();
        sys = SubsystemsCollection.getInstance(hardwareMap);

        autoDrive = new AutoDriveUtility(hardwareMap, sys.driveBase, new Pose2d(17.5, -63.25, Math.toRadians(0)));

        new CommandIntakeSetSlide(Intake.SlidePosition.FULLY_RETRACTED);

        Pose2d baseBarPosition = new Pose2d(24 + (double)(13/2) + 10, -(double)(17.5/2), Math.toRadians(-270));

        waitForStart();

        schedule(new ParallelDeadlineGroup(
                new SequentialCommandGroup(
                    new ParallelCommandGroup(
                        new CommandFollowTrajectories(autoDrive,
                                autoDrive.trajectorySequenceBuilder()
                                    .splineToSplineHeading(baseBarPosition, Math.toRadians(135))
                                ),
                        new CommandExtakeSetLift(Extake.LiftPosition.TOP_BAR)
                    ),
                    new CommandExtakeSetLift(Extake.LiftPosition.DOWN)
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
