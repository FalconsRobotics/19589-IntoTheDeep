package org.firstinspires.ftc.teamcode.opmodes.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.utilities.SubsystemsCollection;

import java.util.function.DoubleSupplier;

public class CommandDriveBaseDriveFieldCentric extends CommandBase {
    private final SubsystemsCollection sys;
    private final DoubleSupplier forward, strafe, rotation, angle;

    public CommandDriveBaseDriveFieldCentric(DoubleSupplier forward, DoubleSupplier strafe,
                                             DoubleSupplier rotation, DoubleSupplier angle) {
        sys = SubsystemsCollection.getInstance(null);
        // addRequirements(sys.driveBase);

        this.forward = forward;
        this.strafe = strafe;
        this.rotation = rotation;
        this.angle = angle;
    }

    public void execute() {
        sys.driveBase.motors.driveFieldCentric(forward.getAsDouble(),
                strafe.getAsDouble(), rotation.getAsDouble(), angle.getAsDouble(), true);
    }
}

