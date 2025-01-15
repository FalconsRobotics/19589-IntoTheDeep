package org.firstinspires.ftc.teamcode.commands;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.external.rrquickstart.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.external.rrquickstart.trajectorysequence.TrajectorySequenceBuilder;
import org.firstinspires.ftc.teamcode.utilities.roadrunner.AutoDriveUtility;

public class CommandFollowTrajectories extends CommandBase {
    private final AutoDriveUtility autoDrive;
    private final TrajectorySequence sequence;
    private boolean ran;

    public CommandFollowTrajectories(AutoDriveUtility autoDrive, TrajectorySequenceBuilder sequenceBuilder) {
        this.autoDrive = autoDrive;
        this.sequence = autoDrive.build(sequenceBuilder);

        ran = false;
    }

    public void initialize() {
        autoDrive.runTrajectorySequence(sequence);
        ran = true;
    }

    public boolean isFinished() {
        return !autoDrive.isBusy();
    }
}
