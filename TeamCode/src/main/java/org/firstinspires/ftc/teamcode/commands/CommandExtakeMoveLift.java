package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.utilities.SubsystemsCollection;

public class CommandExtakeMoveLift extends CommandBase {
    private static final int MAX_DISTANCE = 10;

    private final SubsystemsCollection sys;
    private final int position;

    public CommandExtakeMoveLift(int position) {
        sys = SubsystemsCollection.getInstance(null);
        // addRequirements(sys.extake);

        this.position = position;
    }

    public void initialize() {
        sys.extake.lift.setTarget(position);
    }

    public boolean isFinished() {
        return sys.extake.lift.atTarget();
    }
}
