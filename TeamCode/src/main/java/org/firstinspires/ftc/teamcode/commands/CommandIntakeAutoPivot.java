package org.firstinspires.ftc.teamcode.commands;


import com.arcrobotics.ftclib.command.CommandBase;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.utilities.SubsystemsCollection;
import org.firstinspires.ftc.teamcode.utilities.vision.VisionUtility;

public class CommandIntakeAutoPivot extends CommandBase {
    private final SubsystemsCollection sys;
    private final VisionUtility vision;
    private final double position;

    public CommandIntakeAutoPivot(HardwareMap hardwareMap){
        sys = SubsystemsCollection.getInstance(null);
        vision = new VisionUtility(hardwareMap);
        double angle = vision.findBlockAngle(0);

        if(angle <= 6 || angle >= 170) {
            position = Intake.PivotPosition.MIDDLE;
        } else if(angle > 6 && angle <= 80) {
            position = Intake.PivotPosition.LEFT;
        } else if(angle > 80 && angle <= 120) {
            position = Intake.PivotPosition.RIGHT;
        } else if(angle > 120 && angle < 170){
            position = Intake.PivotPosition.MIDDLE;
        } else {
            position = Intake.PivotPosition.MIDDLE;
        }

    }

    public void initialize(){
        sys.intake.pivot.servo.setPosition(position);
    }

    public boolean isFinished() {
        return true;
    }
}
