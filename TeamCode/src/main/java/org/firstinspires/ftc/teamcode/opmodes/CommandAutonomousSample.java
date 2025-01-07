package org.firstinspires.ftc.teamcode.opmodes;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.commands.CommandExtakeMoveLift;
import org.firstinspires.ftc.teamcode.commands.CommandExtakeRotateArm;
import org.firstinspires.ftc.teamcode.commands.CommandFollowTrajectories;
import org.firstinspires.ftc.teamcode.commands.CommandIntakeRotateArm;
import org.firstinspires.ftc.teamcode.commands.CommandIntakeRotateWheels;
import org.firstinspires.ftc.teamcode.commands.CommandIntakeSetPivot;
import org.firstinspires.ftc.teamcode.commands.CommandRun;
import org.firstinspires.ftc.teamcode.commands.CommandTimer;
import org.firstinspires.ftc.teamcode.subsystems.Extake;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.utilities.SubsystemsCollection;
import org.firstinspires.ftc.teamcode.utilities.roadrunner.AutoDriveUtility;

@Autonomous(name = "Autonomous W.M.D - 4Samples")
public class CommandAutonomousSample extends CommandOpMode {
    private SubsystemsCollection sys;
    private AutoDriveUtility autoDrive;

    public void initialize() {
        SubsystemsCollection.deinit();
        sys = SubsystemsCollection.getInstance(hardwareMap);

        // -40.3, -63
        autoDrive = new AutoDriveUtility(hardwareMap, sys.driveBase, new Pose2d(0, 0, 0));

        /* SequentialCommandGroup goToBucketAndUnload = new SequentialCommandGroup(
                // Use directly after intakeSample.
                new CommandIntakeRotateArm(Intake.ArmPosition.IDLE),
                new ParallelCommandGroup(
                        new CommandExtakeMoveLift(Extake.LiftPosition.TOP_BUCKET),
                        new CommandExtakeRotateArm(Extake.ArmPosition.PREPARE_UNLOAD)
                ),
                new CommandFollowTrajectories(autoDrive,
                        autoDrive.trajectorySequenceBuilder()
                                .lineToLinearHeading(new Pose2d(-57, -55, Math.toRadians(45)))
                ),
                new CommandExtakeRotateArm(Extake.ArmPosition.UNLOAD),
                new CommandTimer(350),
                new ParallelCommandGroup(
                        new CommandExtakeRotateArm(Extake.ArmPosition.LOAD),
                        new CommandExtakeMoveLift(Extake.LiftPosition.DOWN)
                )
        ); */

        /* SequentialCommandGroup intakeSample = new SequentialCommandGroup(
                new ParallelCommandGroup(
                        new CommandIntakeRotateArm(Intake.ArmPosition.PICKUP),
                        new CommandIntakeRotateWheels(.85, 800)
                ),
                new CommandIntakeRotateArm(Intake.ArmPosition.UNLOAD),
                new CommandIntakeRotateWheels(-.45, 500)
        ); */


        schedule(new ParallelCommandGroup(
                new SequentialCommandGroup(
                        // Prepare robot for placing preloaded sample
                        new CommandIntakeRotateArm(Intake.ArmPosition.IDLE),
                        new ParallelCommandGroup(
                                new CommandExtakeMoveLift(Extake.LiftPosition.TOP_BUCKET),
                                new CommandExtakeRotateArm(Extake.ArmPosition.PREPARE_UNLOAD)
                        ),

                        // Go to bucket with spline, necessary for first unload
                        new CommandFollowTrajectories(autoDrive,
                                autoDrive.trajectorySequenceBuilder()
                                        .lineToLinearHeading(new Pose2d(-16.7, 8, Math.toRadians(-45)))
                        ),

                        // Tip bucket and reset extake
                        new CommandExtakeRotateArm(Extake.ArmPosition.UNLOAD),
                        new CommandTimer(350),
                        new ParallelCommandGroup(
                                new CommandExtakeRotateArm(Extake.ArmPosition.LOAD),
                                new CommandExtakeMoveLift(Extake.LiftPosition.DOWN)
                        ),

                        // Go to right-most ground sample and intake it
                        new CommandFollowTrajectories(autoDrive,
                                autoDrive.trajectorySequenceBuilder()
                                        .lineToLinearHeading(new Pose2d(-7.7, 30, 0))
                        ),
                        new CommandIntakeSetPivot(0.15),
                        new ParallelCommandGroup(
                                new CommandIntakeRotateArm(Intake.ArmPosition.PICKUP),
                                new CommandIntakeRotateWheels(.85, 800)
                        ),
                        new CommandIntakeSetPivot(0.5),
                        new CommandIntakeRotateArm(Intake.ArmPosition.UNLOAD),
                        new CommandIntakeRotateWheels(-.45, 500),
                        new CommandIntakeRotateArm(Intake.ArmPosition.IDLE),

                        // Prepare arm for extaking sample #2, navigate to bucket
                        new ParallelCommandGroup(
                                new CommandExtakeMoveLift(Extake.LiftPosition.TOP_BUCKET),
                                new CommandExtakeRotateArm(Extake.ArmPosition.PREPARE_UNLOAD)
                        ),
                        new CommandFollowTrajectories(autoDrive,
                                autoDrive.trajectorySequenceBuilder()
                                        .lineToLinearHeading(new Pose2d(-16.7, 8, Math.toRadians(-45)))
                        ),
                        new CommandExtakeRotateArm(Extake.ArmPosition.UNLOAD),
                        new CommandTimer(350),
                        new ParallelCommandGroup(
                                new CommandExtakeRotateArm(Extake.ArmPosition.LOAD),
                                new CommandExtakeMoveLift(Extake.LiftPosition.DOWN)
                        ),

                        // Drive to center ground sample, intake it
                        new CommandFollowTrajectories(autoDrive,
                                autoDrive.trajectorySequenceBuilder()
                                        .lineToLinearHeading(new Pose2d(-17.7, 30, 0))
                        ),
                        new CommandIntakeSetPivot(0.15),
                        new ParallelCommandGroup(
                                new CommandIntakeRotateArm(Intake.ArmPosition.PICKUP),
                                new CommandIntakeRotateWheels(.85, 800)
                        ),
                        new CommandIntakeSetPivot(0.5),
                        new CommandIntakeRotateArm(Intake.ArmPosition.UNLOAD),
                        new CommandIntakeRotateWheels(-.45, 500),
                        new CommandIntakeRotateArm(Intake.ArmPosition.IDLE),

                        // Navigate to bucket and extake
                        new ParallelCommandGroup(
                                new CommandExtakeMoveLift(Extake.LiftPosition.TOP_BUCKET),
                                new CommandExtakeRotateArm(Extake.ArmPosition.PREPARE_UNLOAD)
                        ),
                        new CommandFollowTrajectories(autoDrive,
                                autoDrive.trajectorySequenceBuilder()
                                        .lineToLinearHeading(new Pose2d(-16.7, 8, Math.toRadians(-45)))
                        ),
                        new CommandExtakeRotateArm(Extake.ArmPosition.UNLOAD),
                        new CommandTimer(350),
                        new ParallelCommandGroup(
                                new CommandExtakeRotateArm(Extake.ArmPosition.LOAD),
                                new CommandExtakeMoveLift(Extake.LiftPosition.DOWN)
                        ),

                        // Navigate to left-most grounded sample. Needs to spline because robot can't pick up the sample head-on
                        new CommandFollowTrajectories(autoDrive,
                                autoDrive.trajectorySequenceBuilder()
                                        .splineToLinearHeading(new Pose2d(-19.7, 39, Math.toRadians(90)), 9)
                        ),
                        new ParallelCommandGroup(
                                new CommandIntakeRotateArm(Intake.ArmPosition.PICKUP),
                                new CommandIntakeRotateWheels(.85, 800)
                        ),
                        new CommandIntakeRotateArm(Intake.ArmPosition.UNLOAD),
                        new CommandIntakeRotateWheels(-.45, 500),
                        new CommandIntakeRotateArm(Intake.ArmPosition.IDLE),

                        // Go back to the bucket and EXTAKE!!!
                        new ParallelCommandGroup(
                                new CommandExtakeMoveLift(Extake.LiftPosition.TOP_BUCKET),
                                new CommandExtakeRotateArm(Extake.ArmPosition.PREPARE_UNLOAD)
                        ),
                        new CommandFollowTrajectories(autoDrive,
                                autoDrive.trajectorySequenceBuilder()
                                        .lineToLinearHeading(new Pose2d(-16.7, 8, Math.toRadians(-45)))
                        ),
                        new CommandExtakeRotateArm(Extake.ArmPosition.UNLOAD),
                        new CommandTimer(350),
                        new ParallelCommandGroup(
                                new CommandExtakeRotateArm(Extake.ArmPosition.LOAD),
                                new CommandExtakeMoveLift(Extake.LiftPosition.DOWN)
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
