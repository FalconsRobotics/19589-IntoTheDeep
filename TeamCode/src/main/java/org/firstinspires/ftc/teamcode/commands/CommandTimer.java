package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.util.Timing;

import java.util.concurrent.TimeUnit;

public class CommandTimer extends CommandBase {
    private final Timing.Timer timer;

    public CommandTimer(int milliseconds) {
        timer = new Timing.Timer(milliseconds, TimeUnit.MILLISECONDS);
        timer.pause(); // Unsure if this is needed.
    }

    public void initialize() {
        timer.start();
    }

    public boolean isFinished() {
        return timer.done();
    }
}
