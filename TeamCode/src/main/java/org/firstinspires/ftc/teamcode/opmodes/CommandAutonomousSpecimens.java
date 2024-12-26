package org.firstinspires.ftc.teamcode.opmodes;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.opmodes.commands.CommandRunContinuous;
import org.firstinspires.ftc.teamcode.utilities.SubsystemsCollection;

@Autonomous(name = "Autonomous W.M.D. - 5specimens")
public class CommandAutonomousSpecimens extends CommandOpMode {
    SubsystemsCollection sys;

    public void initialize() {
        SubsystemsCollection.deinit();
        sys = SubsystemsCollection.getInstance(hardwareMap);
        
        schedule(new CommandRunContinuous() -> {

        });
    }
}