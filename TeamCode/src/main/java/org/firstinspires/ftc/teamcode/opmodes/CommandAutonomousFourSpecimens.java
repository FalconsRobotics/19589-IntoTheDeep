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
import org.firstinspires.ftc.teamcode.commands.CommandIntakeRotateWheels;
import org.firstinspires.ftc.teamcode.commands.CommandIntakeSetArm;
import org.firstinspires.ftc.teamcode.commands.CommandIntakeSetPivot;
import org.firstinspires.ftc.teamcode.commands.CommandIntakeSetSlide;
import org.firstinspires.ftc.teamcode.commands.CommandRun;
import org.firstinspires.ftc.teamcode.commands.CommandTimer;
import org.firstinspires.ftc.teamcode.subsystems.Extake;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.utilities.SubsystemsCollection;
import org.firstinspires.ftc.teamcode.utilities.roadrunner.AutoDriveUtility;

@Autonomous(name = "Autonomous W.M.D. - Four Specimens")
public class CommandAutonomousFourSpecimens extends CommandOpMode {
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
                                new CommandIntakeSetArm(Intake.ArmPosition.IDLE),
                                new CommandIntakeSetPivot(Intake.PivotPosition.MIDDLE),
                                new CommandExtakeSetLift(Extake.LiftPosition.TOP_BAR),
                                new CommandFollowTrajectories(autoDrive,
                                        autoDrive.trajectorySequenceBuilder()
                                                .lineToConstantHeading(new Vector2d(-1, -29))
                                )
                        ),
                        new CommandExtakeSetLift(Extake.LiftPosition.DOWN),
                        new CommandFollowTrajectories(autoDrive,
                                autoDrive.trajectorySequenceBuilder()
                                        .lineToLinearHeading(new Pose2d(36, -38.5, Math.toRadians(45)))
                        ),
                        new ParallelDeadlineGroup(
                                new CommandTimer(500),
                                new CommandIntakeSetSlide(Intake.SlidePosition.EXTENDED),
                                new CommandIntakeSetArm(Intake.ArmPosition.HOVER),
                                new CommandIntakeSetPivot(Intake.PivotPosition.RIGHT_45)
                        ),
                        new ParallelCommandGroup(
                                new CommandIntakeRotateWheels(Intake.WheelPower.LOAD, 750),
                                new CommandIntakeSetArm(Intake.ArmPosition.PICKUP)
                        ),
                        new ParallelCommandGroup(
                                new CommandIntakeSetArm(Intake.ArmPosition.HOVER),
                                new CommandFollowTrajectories(autoDrive,
                                        autoDrive.trajectorySequenceBuilder()
                                                .turn(-90)
                                )
                        ),
                        new CommandIntakeRotateWheels(Intake.WheelPower.UNLOAD, 200)
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
        ));

        telemetry.addLine("Build Complete");
        telemetry.update();
    }


}