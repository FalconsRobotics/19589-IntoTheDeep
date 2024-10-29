package org.firstinspires.ftc.teamcode.opmodes;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.opmodes.commands.CommandDriveRobotCentric;
import org.firstinspires.ftc.teamcode.opmodes.commands.CommandExtakeMoveLift;
import org.firstinspires.ftc.teamcode.subsystems.Extake;
import org.firstinspires.ftc.teamcode.subsystems.SubsystemsCollection;

@TeleOp(name = "Command TeleOp")
public class CommandTeleOp extends CommandOpMode {
    private SubsystemsCollection sys;
    private GamepadEx driverGamepad, utilityGamepad;

    public void initialize() {
        sys = SubsystemsCollection.getInstance(hardwareMap);
        driverGamepad = new GamepadEx(gamepad1);
        utilityGamepad = new GamepadEx(gamepad2);

        register(sys.intake);
        register(sys.extake);

        schedule(new CommandDriveRobotCentric(
                () -> driverGamepad.getLeftY(), () -> driverGamepad.getLeftX(),
                () -> driverGamepad.getRightX()
        ));

        driverGamepad.getGamepadButton(GamepadKeys.Button.A)
                .whenPressed(new CommandExtakeMoveLift(Extake.LiftPosition.DOWM));
    }
}
