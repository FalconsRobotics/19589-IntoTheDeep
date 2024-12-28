package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.external.rrquickstart.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.utilities.roadrunner.AutoDriveUtility;

public class CommandFollowTrajectories extends CommandBase {
    private final AutoDriveUtility autoDrive;
    private final TrajectorySequence sequence;
    private boolean ran;

    public CommandFollowTrajectories(AutoDriveUtility autoDrive, TrajectorySequence sequence) {
        this.autoDrive = autoDrive;
        this.sequence = sequence;
        ran = false;
    }

    public void execute() {
        // Hack because initialize() does not run at the start of executing a command, rather, when
        // you first run it. How fun.
        if (!ran) {
            autoDrive.runTrajectorySequence(sequence);
            ran = true;
        }
    }

    public boolean isFinished() {
        return !autoDrive.isBusy();
    }
}
