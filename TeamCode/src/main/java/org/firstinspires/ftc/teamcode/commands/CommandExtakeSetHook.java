package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.utilities.SubsystemsCollection;

public class CommandExtakeSetHook extends CommandBase {
    private final SubsystemsCollection sys;
    private final double position;

    public CommandExtakeSetHook(double position) {
        sys = SubsystemsCollection.getInstance(null);
        // addRequirements(sys.extake);

        this.position = position;
    }

    public void initialize() {
        sys.extake.hook.setPosition(position);
    }

    public boolean isFinished() {
        return true;
    }
}
