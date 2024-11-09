package org.firstinspires.ftc.teamcode.opmodes.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.ext.GoBildaPinpointDriver;
import org.firstinspires.ftc.teamcode.utilities.SubsystemsCollection;


public class CommandDriveBaseRecalibrateIMU extends CommandBase {
    private final SubsystemsCollection sys;

    public CommandDriveBaseRecalibrateIMU() {
        sys = SubsystemsCollection.getInstance(null);
    }

    public void initialize() {
        sys.driveBase.odometry.recalibrateIMU();
    }

    public void execute() {
        // Ensure no movement while waiting for odometry module.
        sys.driveBase.motors.driveWithMotorPowers(0, 0, 0, 0);
    }

    public boolean isFinished() {
        return sys.driveBase.odometry.getDeviceStatus() == GoBildaPinpointDriver.DeviceStatus.READY;
    }
}