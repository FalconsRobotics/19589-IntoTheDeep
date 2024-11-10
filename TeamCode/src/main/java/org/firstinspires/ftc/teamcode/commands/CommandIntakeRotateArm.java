package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.utilities.SubsystemsCollection;

public class CommandIntakeRotateArm extends CommandBase {
    private static final int MAX_DISTANCE = 10;

    private final SubsystemsCollection sys;
    private final int position;

    public CommandIntakeRotateArm(int position) {
        sys = SubsystemsCollection.getInstance(null);
        // addRequirements(sys.intake);

        this.position = position;
    }

    public void initialize() {
        sys.intake.setArmPosition(position);
    }

    public boolean isFinished() {
        return sys.intake.arm.atTarget();
    }
}
