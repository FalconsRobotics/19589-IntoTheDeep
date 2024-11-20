package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.utilities.SubsystemsCollection;

import java.util.function.DoubleSupplier;

public class CommandDriveBaseDriveFieldCentric extends CommandBase {
    private final SubsystemsCollection sys;
    private final DoubleSupplier forward, strafe, rotation;

    public CommandDriveBaseDriveFieldCentric(DoubleSupplier strafe, DoubleSupplier forward,
                                             DoubleSupplier rotation) {
        sys = SubsystemsCollection.getInstance(null);
        // addRequirements(sys.driveBase);

        this.forward = forward;
        this.strafe = strafe;
        this.rotation = rotation;
    }

    public void execute() {
        sys.driveBase.motors.driveFieldCentric(
                strafe.getAsDouble(), forward.getAsDouble(), rotation.getAsDouble(),
                // For some reason FTCLib requires degrees, but does not tell you this fact.
                sys.driveBase.odometry.getHeading() * (180 / Math.PI),
                true
        );
    }

    public void end() {
        sys.driveBase.brake(true);
    }
}

