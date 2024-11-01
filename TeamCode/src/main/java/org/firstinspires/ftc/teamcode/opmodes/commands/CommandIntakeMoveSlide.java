package org.firstinspires.ftc.teamcode.opmodes.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.SubsystemsCollection;

public class CommandIntakeMoveSlide extends CommandBase {
    private final SubsystemsCollection sys;
    private final double position;

    public CommandIntakeMoveSlide(double position) {
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
