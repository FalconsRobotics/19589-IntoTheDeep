package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.utilities.SubsystemsCollection;
import org.firstinspires.ftc.teamcode.utilities.vision.VisionUtility;

public class CommandLimelightStatus extends CommandBase {
    private final VisionUtility vision;
    private final LimelightStatus state;
    public enum LimelightStatus{

        Start,
        Stop,
        Pause
    }

    public CommandLimelightStatus(HardwareMap hardwareMap, LimelightStatus LLS){
        vision = new VisionUtility(hardwareMap);
        state = LLS;
    }

    public void initialize() {
        if(state == LimelightStatus.Start){
            vision.limelight.start();
        } else if(state == LimelightStatus.Pause){
            vision.limelight.pause();
        } else {
            vision.limelight.stop();
        }

    }

    public boolean isFinished() {
        return true;
    }
}
