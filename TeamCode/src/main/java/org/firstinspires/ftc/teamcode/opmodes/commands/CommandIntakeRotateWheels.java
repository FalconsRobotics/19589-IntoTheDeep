package org.firstinspires.ftc.teamcode.opmodes.commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.util.Timing;

import org.firstinspires.ftc.teamcode.utilities.SubsystemsCollection;

import java.util.concurrent.TimeUnit;

public class CommandIntakeRotateWheels extends CommandBase {
    private final SubsystemsCollection sys;
    private final double power;
    private final Timing.Timer timer;

    // TODO - Make this run on a timer internally.
    public CommandIntakeRotateWheels(double power, int millis) {
        sys = SubsystemsCollection.getInstance(null);
        this.power = power;

        timer = new Timing.Timer(millis, TimeUnit.MILLISECONDS);
    }

    public void initialize() {
        sys.intake.setWheelPower(power);
        timer.start();
    }

    public void end(boolean interrupted) {
        sys.intake.setWheelPower(0);
    }

    public boolean isFinished() {
        return timer.done();
    }
}
