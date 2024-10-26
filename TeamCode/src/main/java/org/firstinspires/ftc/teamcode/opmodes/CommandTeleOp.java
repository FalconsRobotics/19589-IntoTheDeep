package org.firstinspires.ftc.teamcode.opmodes;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.opmodes.commands.CommandDriveRobotCentric;
import org.firstinspires.ftc.teamcode.subsystems.SubsystemsCollection;

@TeleOp(name = "Command TeleOp")
public class CommandTeleOp extends CommandOpMode {
    private SubsystemsCollection sys;
    private GamepadEx pad1, pad2;

    public void initialize() {
        sys = SubsystemsCollection.getInstance(hardwareMap);
        pad1 = new GamepadEx(gamepad1);
        pad2 = new GamepadEx(gamepad2);

        schedule(new CommandDriveRobotCentric(
                () -> pad1.getLeftY(), () -> pad1.getLeftX(), () -> pad1.getRightX()
        ));

        register(sys.intake);
        register(sys.extake);
    }
}
