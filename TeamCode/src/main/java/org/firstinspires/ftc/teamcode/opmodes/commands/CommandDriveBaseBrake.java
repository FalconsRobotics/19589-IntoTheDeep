package org.firstinspires.ftc.teamcode.opmodes.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.utilities.SubsystemsCollection;

public class CommandDriveBaseBrake extends CommandBase {
    private final SubsystemsCollection sys;
    private final boolean toggle;

    public CommandDriveBaseBrake(boolean toggle) {
        sys = SubsystemsCollection.getInstance(null);
        // addRequirements(sys.driveBase);

        this.toggle = toggle;
    }

    public void initialize() {
        sys.driveBase.brake(toggle);
    }

    public boolean isFinished() {
        return true;
    }
}
