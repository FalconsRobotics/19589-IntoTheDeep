package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.utilities.SubsystemsCollection;

public class CommandExtakeSetClimb extends CommandBase {
    private final SubsystemsCollection sys;
    private final double position;

    public CommandExtakeSetClimb(double position) {
        sys = SubsystemsCollection.getInstance(null);
        // addRequirements(sys.extake);

        this.position = position;
    }

    public void initialize() {
        sys.extake.climb.setPosition(position);
    }

    public boolean isFinished() {
        return true;
    }
}
