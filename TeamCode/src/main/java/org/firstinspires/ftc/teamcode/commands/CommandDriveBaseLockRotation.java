package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.utilities.SubsystemsCollection;


public class CommandDriveBaseLockRotation extends CommandBase {
    private final SubsystemsCollection sys;
    private final double heading;

    public CommandDriveBaseLockRotation(double heading) {
        sys = SubsystemsCollection.getInstance(null);
        // addRequirements(sys.driveBase);

        this.heading = heading;
    }

    public void execute() {
        sys.driveBase.lockRotation(heading);
    }

    public boolean isFinished() {
        return true;
    }
}
