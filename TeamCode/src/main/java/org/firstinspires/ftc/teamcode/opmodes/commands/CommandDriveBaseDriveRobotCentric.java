package org.firstinspires.ftc.teamcode.opmodes.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.SubsystemsCollection;

import java.util.function.DoubleSupplier;

public class CommandDriveBaseDriveRobotCentric extends CommandBase {
    private final SubsystemsCollection sys;
    private final DoubleSupplier forward, strafe, rotation;

    public CommandDriveBaseDriveRobotCentric(DoubleSupplier forward, DoubleSupplier strafe, DoubleSupplier rotation) {
        sys = SubsystemsCollection.getInstance(null);
        addRequirements(sys.driveBase);

        this.forward = forward;
        this.strafe = strafe;
        this.rotation = rotation;
    }

    public void execute() {
        sys.driveBase.motors.driveRobotCentric(forward.getAsDouble(),
                strafe.getAsDouble(), rotation.getAsDouble(), true);
    }
}
