
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

        waitForStart();

        while (opModeIsActive()) {
            sys.periodic();

            sys.driveBase.motors.driveFieldCentric(pad.getLeftX(), pad.getLeftY(), pad.getRightX(), sys.driveBase.odometry.getHeading());

            if (pad.wasJustPressed(GamepadKeys.Button.X)) {
                sys.driveBase.odometry.recalibrateIMU();
                sys.driveBase.odometry.resetPosAndIMU();
                sleep(1000);
            }

            telemetry.addData("X Position", sys.driveBase.odometry.getPosX());
            telemetry.addData("Y Position", sys.driveBase.odometry.getPosY());
            telemetry.addData("Heading", sys.driveBase.odometry.getHeading());
            telemetry.update();
        }
    }


}
