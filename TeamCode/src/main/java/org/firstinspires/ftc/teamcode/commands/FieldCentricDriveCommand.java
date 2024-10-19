package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.DriveBase;

import java.util.function.DoubleSupplier;

public class FieldCentricDriveCommand extends CommandBase {
    private final DriveBase driveBase;
    // Double suppliers used for lambda function functionality.
    private DoubleSupplier forwardPower, strafePower, rotationPower;

    public FieldCentricDriveCommand(DriveBase subsystem, DoubleSupplier forward, DoubleSupplier strafe, DoubleSupplier rotation) {
        driveBase = subsystem;
        forwardPower = forward;
        strafePower = strafe;
        rotationPower = rotation;

        addRequirements(driveBase);
    }

    public void execute() {
        // driveBase.setVelocityFieldCentric(forwardPower.getAsDouble(), strafePower.getAsDouble(), rotationPower.getAsDouble());
    }

    public boolean isFinished() { return true; }
}
