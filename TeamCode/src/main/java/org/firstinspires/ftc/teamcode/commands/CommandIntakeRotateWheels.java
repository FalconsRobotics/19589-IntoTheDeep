package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.util.Timing;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.utilities.SubsystemsCollection;

import java.util.concurrent.TimeUnit;

public class CommandIntakeRotateWheels extends CommandBase {
    private final SubsystemsCollection sys;
    private final double power;
    private final Timing.Timer timer;

    private boolean finishPrematurely;

    public CommandIntakeRotateWheels(double power, int millis) {
        sys = SubsystemsCollection.getInstance(null);
        this.power = power;

        timer = new Timing.Timer(millis, TimeUnit.MILLISECONDS);

        finishPrematurely = false;
    }

    public void initialize() {
        sys.intake.setWheelPower(power);
        timer.start();
    }

    public void execute() {
        // hack!
        if (power > 0.0) {
            finishPrematurely = sys.intake.sampleColor.getDistance(DistanceUnit.CM) < 2.25;
        }
    }

    public void end(boolean interrupted) {
        sys.intake.setWheelPower(Intake.WheelPower.STOP);
    }

    public boolean isFinished() {
        // hack!!!!
        return timer.done() || finishPrematurely;
    }
}
