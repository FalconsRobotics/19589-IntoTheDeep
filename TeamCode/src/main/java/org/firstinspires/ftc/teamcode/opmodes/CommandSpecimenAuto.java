package org.firstinspires.ftc.teamcode.opmodes;

import static org.firstinspires.ftc.teamcode.subsystems.Intake.PivotPosition.RIGHT_45;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.ParallelDeadlineGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.commands.CommandExtakeSetLift;
import org.firstinspires.ftc.teamcode.commands.CommandFollowTrajectories;
import org.firstinspires.ftc.teamcode.commands.CommandIntakeRotateWheels;
import org.firstinspires.ftc.teamcode.commands.CommandIntakeSetArm;
import org.firstinspires.ftc.teamcode.commands.CommandIntakeSetPivot;
import org.firstinspires.ftc.teamcode.commands.CommandIntakeSetSlide;
import org.firstinspires.ftc.teamcode.commands.CommandRun;
import org.firstinspires.ftc.teamcode.subsystems.Extake;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.utilities.SubsystemsCollection;
import org.firstinspires.ftc.teamcode.utilities.roadrunner.AutoDriveUtility;

@Config
@Autonomous(name = "CRI Specimen", preselectTeleOp = "Command TeleOp")
public class CommandSpecimenAuto extends CommandOpMode {
    private SubsystemsCollection sys;
    private AutoDriveUtility autoDrive;

    public static double sp1h = 90.0;
    public static double sp2h = 180.0;

    public void initialize() {
        SubsystemsCollection.deinit();
        sys = SubsystemsCollection.getInstance(hardwareMap);

        Pose2d botStartingPosition = new Pose2d(17.5, -63.25, Math.toRadians(180));
        Pose2d baseBarPosition = new Pose2d(botStartingPosition.getX() + 18, botStartingPosition.getY() + 49, Math.toRadians(270));
        Pose2d baseBarIntermediatePathPosition = new Pose2d(botStartingPosition.getX() + 22, botStartingPosition.getY() + ((double) 23 /2), Math.toRadians(270));

        Pose2d leftCloseSample = new Pose2d(botStartingPosition.getX() + 19, botStartingPosition.getY() + 23, Math.toRadians(60));
        Pose2d leftMiddleSample = new Pose2d(botStartingPosition.getX() + 21, botStartingPosition.getY() + 33, Math.toRadians(60));
        Pose2d leftFarSample = new Pose2d(botStartingPosition.getX() + 22, botStartingPosition.getY() + 43, Math.toRadians(60));

        Pose2d rightCloseSampleFromRung = new Pose2d(botStartingPosition.getX() + 19, botStartingPosition.getY() + 23, Math.toRadians(-30));

        autoDrive = new AutoDriveUtility(hardwareMap, sys.driveBase, botStartingPosition);

        new CommandIntakeSetSlide(Intake.SlidePosition.FULLY_RETRACTED);

        waitForStart();

        schedule(new ParallelDeadlineGroup(
                new SequentialCommandGroup(
                    //<editor-fold>
                    new ParallelDeadlineGroup(
                            new CommandFollowTrajectories(autoDrive,
                                    autoDrive.trajectorySequenceBuilder()
                                            .splineToLinearHeading(baseBarIntermediatePathPosition, Math.toRadians(90))
//                                            .splineToSplineHeading(baseBarPosition, Math.toRadians(90))),
                                            .back(37.5)
                                            .strafeRight(3)
                            ),
                            new CommandExtakeSetLift(Extake.LiftPosition.TOP_BAR),
                            new CommandIntakeSetArm(Intake.ArmPosition.IDLE)
                    ),
                    new ParallelCommandGroup(
                            new CommandExtakeSetLift(Extake.LiftPosition.DOWN),
                            new CommandIntakeSetSlide(0.9),
                            new CommandIntakeSetPivot(RIGHT_45),
                            new CommandFollowTrajectories(autoDrive,
                                    autoDrive.trajectorySequenceBuilder()
                                            .strafeLeft(3)
                                            .turn(Math.toRadians(60)))
                    ),
                    new ParallelDeadlineGroup(
                            new CommandIntakeRotateWheels(Intake.WheelPower.LOAD, 500),
                            new CommandIntakeSetArm(Intake.ArmPosition.PICKUP)
                    ),
                    new ParallelDeadlineGroup(
                            new CommandFollowTrajectories(autoDrive,
                                    autoDrive.trajectorySequenceBuilder()
                                            .lineToLinearHeading(new Pose2d(45, -4, Math.toRadians(0)))
                                            .lineToLinearHeading(new Pose2d(48 - (17.5 / 2) + 4.5, -60.25))
                            ),
                            new CommandIntakeSetArm(Intake.ArmPosition.HOVER)
                    ),
                    new ParallelCommandGroup(
                            new CommandIntakeRotateWheels(Intake.WheelPower.UNLOAD, 500),
                            new CommandIntakeSetArm(Intake.ArmPosition.IDLE),
                            new CommandFollowTrajectories(autoDrive,
                                    autoDrive.trajectorySequenceBuilder().strafeRight(3.5))
                    ),
                    new CommandExtakeSetLift(Extake.LiftPosition.LOWER_BUCKET),
                    new ParallelCommandGroup(
                            new CommandFollowTrajectories(autoDrive,
                                    autoDrive.trajectorySequenceBuilder()
                                            .splineToLinearHeading(new Pose2d(38, -48, Math.toRadians(270)), Math.toRadians(0))
//                                            .lineToConstantHeading(new Vector2d(38, -12))),
                                            .lineToConstantHeading(new Vector2d(40, -14))
                                            .strafeRight(4)
                            ),
                            new CommandExtakeSetLift(Extake.LiftPosition.TOP_BAR)
                    ),
                    new CommandExtakeSetLift(Extake.LiftPosition.DOWN),
                    new CommandFollowTrajectories(autoDrive,
                            autoDrive.trajectorySequenceBuilder()
                                    .lineToLinearHeading(new Pose2d(45, -4, Math.toRadians(0)))
                                    .lineToLinearHeading(new Pose2d(48 - (17.5 / 2) + 4.5, -63.25))
                    ),
                    new ParallelCommandGroup(
                            new CommandFollowTrajectories(autoDrive,
                                    autoDrive.trajectorySequenceBuilder()
                                            .splineToLinearHeading(new Pose2d(38, -48, Math.toRadians(270)), Math.toRadians(0))
//                                            .lineToConstantHeading(new Vector2d(38, -12))),
                                            .lineToConstantHeading(new Vector2d(40, -14))
                                            .strafeRight(4)
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
