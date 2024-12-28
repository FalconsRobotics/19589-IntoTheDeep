package org.firstinspires.ftc.teamcode.opmodes;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.commands.CommandExtakeMoveLift;
import org.firstinspires.ftc.teamcode.commands.CommandFollowTrajectories;
import org.firstinspires.ftc.teamcode.commands.CommandRun;
import org.firstinspires.ftc.teamcode.commands.CommandTimer;
import org.firstinspires.ftc.teamcode.subsystems.Extake;
import org.firstinspires.ftc.teamcode.utilities.SubsystemsCollection;
import org.firstinspires.ftc.teamcode.utilities.roadrunner.AutoDriveUtility;

@Autonomous(name = "")
public class CommandAutonomousSample extends CommandOpMode {
    private SubsystemsCollection sys;
    private AutoDriveUtility autoDrive;

    public void initialize() {
        SubsystemsCollection.deinit();
        sys = SubsystemsCollection.getInstance(hardwareMap);

        autoDrive = new AutoDriveUtility(hardwareMap, sys.driveBase);

        waitForStart();

        schedule(new SequentialCommandGroup(
                new CommandFollowTrajectories(autoDrive,
                        autoDrive.trajectorySequenceBuilder()
                                .forward(36)
                                .turn(Math.toRadians(90))
                                .build()
                ),
                new CommandExtakeMoveLift(Extake.LiftPosition.TOP_BAR),
                new CommandTimer(500),
                new CommandExtakeMoveLift(Extake.LiftPosition.DOWN)
        ));
    }
}
