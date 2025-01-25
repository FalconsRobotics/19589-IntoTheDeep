package org.firstinspires.ftc.teamcode.commands;

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

    private static final double NO_POSITION_FOUND = 1.0;
    public CommandAutoStrafe(HardwareMap hardwareMap){
        sys = SubsystemsCollection.getInstance(null);
        vision = new VisionUtility(hardwareMap);
    }

    public void execute() {
        distance = vision.findStrafeToBlock();

        while(distance > 2 || distance < -2){
            final double power = .2 + distance * 0.05;
            new CommandDriveBaseDriveFieldCentric(
                    () -> power,
                    () -> 0,
                    () -> 0
            );
            distance = vision.findStrafeToBlock();
        }

    }

    public boolean isFinished() {
        return distance > 2 || distance < -2;
    }
}
