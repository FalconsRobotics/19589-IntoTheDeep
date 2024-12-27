package org.firstinspires.ftc.teamcode.opmodes;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.utilities.SubsystemsCollection;

@Autonomous(name = "")
public class CommandAutonomousSample extends CommandOpMode {
    SubsystemsCollection sys;

    @Override
    public void initialize() {
        SubsystemsCollection.deinit();
        sys = SubsystemsCollection.getInstance(hardwareMap);

        schedule(new SequentialCommandGroup(

        ));
    }
}
