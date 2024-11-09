package org.firstinspires.ftc.teamcode.opmodes.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.utilities.SubsystemsCollection;

import java.util.function.DoubleSupplier;

public class CommandIntakeMoveSlide extends CommandBase {
    private final SubsystemsCollection sys;
    private final DoubleSupplier position;

    public CommandIntakeMoveSlide(DoubleSupplier position) {
        sys = SubsystemsCollection.getInstance(null);
        // addRequirements(sys.intake);

        this.position = position;
    }

    public void initialize() {
        sys.intake.setSlidePosition(position.getAsDouble());
    }

    public boolean isFinished() {
        return true;
    }
}
