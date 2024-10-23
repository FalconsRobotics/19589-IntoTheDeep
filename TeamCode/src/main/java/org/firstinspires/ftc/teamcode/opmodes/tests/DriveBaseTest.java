
package org.firstinspires.ftc.teamcode.opmodes.tests;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.SubsystemsCollection;

@TeleOp(name = "Test - Drive Base")
public class DriveBaseTest extends LinearOpMode {
    public void runOpMode() {
        SubsystemsCollection sys = SubsystemsCollection.getInstance(hardwareMap);
        GamepadEx pad = new GamepadEx(gamepad1);

        boolean driveModeRobotCentric = true;

        waitForStart();

        while (opModeIsActive()) {
            sys.periodic();

            if (pad.wasJustPressed(GamepadKeys.Button.A)) {
                driveModeRobotCentric = true;
            } else if (pad.wasJustPressed(GamepadKeys.Button.B)) {
                driveModeRobotCentric = false;
            }

            if (driveModeRobotCentric) {
                sys.driveBase.motors.driveRobotCentric(pad.getLeftY(), pad.getLeftX(), pad.getRightX());
            } else {
                // TODO: Odometry
                sys.driveBase.motors.driveFieldCentric(pad.getLeftY(), pad.getLeftX(), pad.getRightX(), sys.driveBase.odometry.getHeading());
            }
        }
    }


}
