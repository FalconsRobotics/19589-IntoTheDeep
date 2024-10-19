package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.DriveBase;

import java.util.function.DoubleSupplier;

public class RobotCentricDriveCommand extends CommandBase {
    private final DriveBase driveBase;
    private DoubleSupplier forwardPower, strafePower, rotationPower;

    public RobotCentricDriveCommand(DriveBase subsystem, DoubleSupplier forward, DoubleSupplier strafe, DoubleSupplier rotation) {
        driveBase = subsystem;
        forwardPower = forward;
        strafePower = strafe;
        rotationPower = rotation;
    }

    public void execute() {
        driveBase.setVelocity(forwardPower.getAsDouble(), strafePower.getAsDouble(), rotationPower.getAsDouble());
    }

    public boolean isFinished() { return true; }
}
