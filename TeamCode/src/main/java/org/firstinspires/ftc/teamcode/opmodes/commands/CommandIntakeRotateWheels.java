package org.firstinspires.ftc.teamcode.opmodes.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.SubsystemsCollection;

public class CommandIntakeRotateWheels extends CommandBase {
    private final SubsystemsCollection sys;
    private final double power;

    // TODO - Make this run on a timer internally.
    public CommandIntakeRotateWheels(double power) {
        sys = SubsystemsCollection.getInstance(null);
        this.power = power;
    }

    public void initialize() {
        sys.intake.setWheelPower(power);
    }

    public void end(boolean interrupted) {
        sys.intake.setWheelPower(0);
    }

    public boolean isFinished() {
        return false;
    }
}
