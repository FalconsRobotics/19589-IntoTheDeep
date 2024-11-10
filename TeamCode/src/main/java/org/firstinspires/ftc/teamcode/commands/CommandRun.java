package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import java.util.function.BooleanSupplier;

public class CommandRun extends CommandBase {
    private final BooleanSupplier lambda;
    private boolean returned;

    public CommandRun(BooleanSupplier lambda) {
        this.lambda = lambda;
    }

    public void execute() {
        returned = lambda.getAsBoolean();
    }

    public boolean isFinished() {
        return returned;
    }
}
