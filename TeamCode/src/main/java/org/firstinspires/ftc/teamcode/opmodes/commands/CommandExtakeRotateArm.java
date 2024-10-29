package org.firstinspires.ftc.teamcode.opmodes.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.SubsystemsCollection;

public class CommandExtakeRotateArm extends CommandBase {
    private final SubsystemsCollection sys;
    private final double position;

    public CommandExtakeRotateArm(double position) {
        sys = SubsystemsCollection.getInstance(null);
        addRequirements(sys.extake);

        this.position = position;
    }

    public void initialize() {
        sys.extake.setArmPosition(position);
    }

    public boolean isFinished() {
        return true;
    }
}
