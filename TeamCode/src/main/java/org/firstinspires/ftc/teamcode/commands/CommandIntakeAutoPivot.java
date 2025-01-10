package org.firstinspires.ftc.teamcode.commands;


import com.arcrobotics.ftclib.command.CommandBase;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.utilities.SubsystemsCollection;
import org.firstinspires.ftc.teamcode.utilities.vision.VisionUtility;

public class CommandIntakeAutoPivot extends CommandBase {
    private final SubsystemsCollection sys;
    private final VisionUtility vision;
    private double position;

    private static final double NO_POSITION_FOUND = 1.0;

    public CommandIntakeAutoPivot(HardwareMap hardwareMap){
        sys = SubsystemsCollection.getInstance(null);
        vision = new VisionUtility(hardwareMap);
        position = NO_POSITION_FOUND;
    }

    public void execute() {
        double angle = vision.findBlockAngle(0);
        if (angle == 1.0) return;

        if(angle > 6 && angle <= 80) {
            position = Intake.PivotPosition.RIGHT;
        } else {
            position = Intake.PivotPosition.MIDDLE;
        }



        sys.intake.pivot.servo.setPosition(position);
    }

    public boolean isFinished() {
        return position != NO_POSITION_FOUND;
    }
}
