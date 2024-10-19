
package org.firstinspires.ftc.teamcode.opmodes.tests;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.command.CommandOpMode;

import org.firstinspires.ftc.teamcode.commands.FieldCentricDriveCommand;
import org.firstinspires.ftc.teamcode.commands.RobotCentricDriveCommand;
import org.firstinspires.ftc.teamcode.subsystems.SubsystemsCollection;


public class DriveBaseTest extends CommandOpMode {
    private SubsystemsCollection sys;
    private GamepadEx pad;

    private boolean movementModeRobotCentric = true;

    private RobotCentricDriveCommand robotCentric;
    private FieldCentricDriveCommand fieldCentric;

    public void initialize() {
        sys = SubsystemsCollection.getInstance(hardwareMap);
        pad = new GamepadEx(gamepad1);

        robotCentric = new RobotCentricDriveCommand(sys.driveBase, () -> -pad.getLeftY(), () -> pad.getLeftX(), () -> pad.getRightX());
        fieldCentric = new FieldCentricDriveCommand(sys.driveBase, () -> -pad.getLeftY(), () -> pad.getLeftX(), () -> pad.getRightX());

        register(sys.driveBase);
        sys.driveBase.setDefaultCommand(robotCentric);
    }


}
