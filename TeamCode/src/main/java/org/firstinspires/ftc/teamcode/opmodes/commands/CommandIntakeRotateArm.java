package org.firstinspires.ftc.teamcode.opmodes.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.SubsystemsCollection;

public class CommandIntakeRotateArm extends CommandBase {
    private static final int MAX_DISTANCE = 10;

    private final SubsystemsCollection sys;
    private final int position;

    public CommandIntakeRotateArm(int position) {
        sys = SubsystemsCollection.getInstance(null);
        addRequirements(sys.intake);

        this.position = position;
    }

    public void initialize() {
        sys.intake.arm.setTargetPosition(position);
    }

    public boolean isFinished() {
        int current = sys.intake.arm.getCurrentPosition();
        return Math.abs(position - current) <= MAX_DISTANCE;
    }
}
