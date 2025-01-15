package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.utilities.SubsystemsCollection;


public class CommandIntakeSetPivot extends CommandBase {
    private final SubsystemsCollection sys;
    private final double position;

    public CommandIntakeSetPivot(double position) {
        sys = SubsystemsCollection.getInstance(null);
        // addRequirements(sys.intake);

        this.position = position;
    }

    public void initialize() {
        sys.intake.pivot.servo.setPosition(position);
    }

    public boolean isFinished() {
        return true;
    }
}