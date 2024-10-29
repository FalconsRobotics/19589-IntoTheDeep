package org.firstinspires.ftc.teamcode.opmodes.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.SubsystemsCollection;

public class CommandExtakeMoveLift extends CommandBase {
    private final int MAX_DISTANCE = 50;

    private final SubsystemsCollection sys;
    private final int position;

    public CommandExtakeMoveLift(int position) {
        sys = SubsystemsCollection.getInstance(null);
        addRequirements(sys.extake);

        this.position = position;
    }

    public void initialize() {
        sys.extake.lift.setTargetPosition(position);
    }

    public boolean isFinished() {
        int current = sys.extake.lift.getCurrentPosition();
        return Math.abs(position - current) <= MAX_DISTANCE;
    }
}
