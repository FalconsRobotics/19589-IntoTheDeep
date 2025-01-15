package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.utilities.SubsystemsCollection;

import java.util.function.DoubleSupplier;

public class CommandIntakeSetSlide extends CommandBase {
    private final SubsystemsCollection sys;
    private final double position;

    public CommandIntakeSetSlide(double position) {
        sys = SubsystemsCollection.getInstance(null);
        // addRequirements(sys.intake);

        this.position = position;
    }

    public void initialize() {
        sys.intake.setSlidePosition(position);
    }

    public boolean isFinished() {
        return true;
    }
}
