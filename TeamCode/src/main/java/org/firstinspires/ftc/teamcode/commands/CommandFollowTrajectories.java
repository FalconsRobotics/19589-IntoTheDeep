package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.external.rrquickstart.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.utilities.roadrunner.AutoDriveUtility;

public class CommandFollowTrajectories extends CommandBase {
    private final AutoDriveUtility autoDrive;
    private final TrajectorySequence sequence;

    public CommandFollowTrajectories(AutoDriveUtility autoDrive, TrajectorySequence sequence) {
        this.autoDrive = autoDrive;
        this.sequence = sequence;
    }

    public void initialize() {
        autoDrive.runTrajectorySequence(sequence);
    }

    // Nothing to run per loop in main thread.

    public boolean isFinished() {
        return !autoDrive.isBusy();
    }
}
