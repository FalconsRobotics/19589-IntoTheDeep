package org.firstinspires.ftc.teamcode.commands;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.utilities.SubsystemsCollection;

import java.util.function.DoubleSupplier;

public class CommandDriveBaseDriveRobotCentric extends CommandBase {
    private final SubsystemsCollection sys;
    private final DoubleSupplier forward, strafe, rotation;

    public CommandDriveBaseDriveRobotCentric(DoubleSupplier strafe, DoubleSupplier forward, DoubleSupplier rotation) {
        sys = SubsystemsCollection.getInstance(null);
        // addRequirements(sys.driveBase);

        this.forward = forward;
        this.strafe = strafe;
        this.rotation = rotation;
    }

    public void execute() {
        sys.driveBase.driveRobotCentric = true;
        sys.driveBase.motorPowers = new Pose2d(
                forward.getAsDouble(), strafe.getAsDouble(), rotation.getAsDouble()
        );
    }
}
