package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.utilities.Clamp;
import org.firstinspires.ftc.teamcode.utilities.SubsystemsCollection;
import org.firstinspires.ftc.teamcode.utilities.vision.VisionUtility;

public class CommandIntakeAutoSlide extends CommandBase {
    private final SubsystemsCollection sys;
    private final VisionUtility vision;
    private double position;

    private static final double NO_POSITION_FOUND = 1.0;

    public CommandIntakeAutoSlide(HardwareMap hardwareMap){
        sys = SubsystemsCollection.getInstance(null);
        vision = new VisionUtility(hardwareMap);
        position = NO_POSITION_FOUND;
    }

    public void execute(){
        double distance = vision.findDistanceToBlock();
        while(distance > 2 || distance < -2){
            position += .05 * Math.signum(distance);
            sys.intake.moveSlidePosition(position);
            sys.intake.setSlidePosition(Clamp.clamp(sys.intake.leftSlide.servo.getPosition(), Intake.SlidePosition.EXTENDED, Intake.SlidePosition.RETRACTED));
            distance = vision.findDistanceToBlock();
        }
    }
}
