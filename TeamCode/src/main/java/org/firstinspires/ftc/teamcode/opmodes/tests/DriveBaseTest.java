
package org.firstinspires.ftc.teamcode.opmodes.tests;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.utilities.SubsystemsCollection;

@TeleOp(name = "Test - Drive Base", group = "Tests")
public class DriveBaseTest extends LinearOpMode {
    public void runOpMode() {
        SubsystemsCollection.deinit();
        SubsystemsCollection sys = SubsystemsCollection.getInstance(hardwareMap);
        GamepadEx pad = new GamepadEx(gamepad1);

        waitForStart();

        while (opModeIsActive()) {
            sys.periodic();

            sys.driveBase.motorPowers = new Pose2d(pad.getLeftY(), pad.getLeftX(), 0.0);
            sys.driveBase.lockRotation(Math.PI / 2);

            if (pad.wasJustPressed(GamepadKeys.Button.X)) {
                sys.driveBase.odometry.recalibrateIMU();
                sys.driveBase.odometry.resetPosAndIMU();
                sleep(1000);
            }

            telemetry.addData("X Position", sys.driveBase.odometry.getPosX());
            telemetry.addData("Y Position", sys.driveBase.odometry.getPosY());
            telemetry.addData("Heading", sys.driveBase.odometry.getHeading());
            telemetry.addData("Target Heading", Math.PI / 2);
            telemetry.update();
        }
    }


}
