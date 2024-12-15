package org.firstinspires.ftc.teamcode.commands;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.external.GoBildaPinpointDriver;
import org.firstinspires.ftc.teamcode.utilities.SubsystemsCollection;


public class CommandDriveBaseRecalibrate extends CommandBase {
    private final SubsystemsCollection sys;

    public CommandDriveBaseRecalibrate() {
        sys = SubsystemsCollection.getInstance(null);
    }

    public void initialize() {
        sys.driveBase.odometry.resetPosAndIMU();
    }

    public void execute() {
        // Ensure no movement while waiting for odometry module.
        sys.driveBase.motorPowers = new Pose2d(0, 0, 0);
    }

    public boolean isFinished() {
        return sys.driveBase.odometry.getDeviceStatus() == GoBildaPinpointDriver.DeviceStatus.READY;
    }
}
