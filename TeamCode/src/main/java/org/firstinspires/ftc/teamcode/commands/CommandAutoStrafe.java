package org.firstinspires.ftc.teamcode.commands;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.arcrobotics.ftclib.command.CommandBase;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.utilities.Clamp;
import org.firstinspires.ftc.teamcode.utilities.SubsystemsCollection;
import org.firstinspires.ftc.teamcode.utilities.vision.VisionUtility;

public class CommandAutoStrafe extends CommandBase {
    private final SubsystemsCollection sys;
    private final VisionUtility vision;

    private double distance = 0.0;

    // For setting
    private final boolean useExternalDriveCommands;
    private final boolean driveRobotCentric;

    private static final double NO_POSITION_FOUND = 1.0;
    public CommandAutoStrafe(HardwareMap hardwareMap){
        sys = SubsystemsCollection.getInstance(null);
        vision = new VisionUtility(hardwareMap);

        useExternalDriveCommands = sys.driveBase.useExternalDriveCommands;
        driveRobotCentric = sys.driveBase.driveRobotCentric;

        sys.driveBase.useExternalDriveCommands = false;
        sys.driveBase.driveRobotCentric = true;
    }

    public void execute() {
        distance = vision.findStrafeToBlock();
        sys.driveBase.motorPowers = new Pose2d(0.0, 0.0, 0.0);

        if (distance > 2 || distance < -2) {
            final double power = .2 + distance * 0.05;
            sys.driveBase.motorPowers = new Pose2d(0, power, 0);
        }
    }

    public void end(boolean interrupted) {
        sys.driveBase.useExternalDriveCommands = useExternalDriveCommands;
        sys.driveBase.driveRobotCentric = driveRobotCentric;
    }

    public boolean isFinished() {
        return distance < 2 && distance > -2;
    }
}
