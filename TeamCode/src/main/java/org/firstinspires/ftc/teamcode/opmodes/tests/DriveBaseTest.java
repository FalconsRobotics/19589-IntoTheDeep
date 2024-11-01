
package org.firstinspires.ftc.teamcode.opmodes.tests;

import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.SubsystemsCollection;

@TeleOp(name = "Test - Drive Base")
public class DriveBaseTest extends LinearOpMode {
    public void runOpMode() {
        SubsystemsCollection.deinit();
        SubsystemsCollection sys = SubsystemsCollection.getInstance(hardwareMap);
        GamepadEx pad = new GamepadEx(gamepad1);

        waitForStart();

        while (opModeIsActive()) {
            sys.periodic();

            sys.driveBase.motors.driveRobotCentric(pad.getLeftX(), pad.getLeftY(), pad.getRightX());

            if (pad.wasJustPressed(GamepadKeys.Button.X)) {
                sys.driveBase.odometry.recalibrateIMU();
                sys.driveBase.odometry.resetPosAndIMU();
                sleep(1000);
            }

            sys.driveBase.brake(pad.getButton(GamepadKeys.Button.LEFT_BUMPER));

            telemetry.addData("X Position", sys.driveBase.odometry.getPosX());
            telemetry.addData("Y Position", sys.driveBase.odometry.getPosY());
            telemetry.addData("Heading", sys.driveBase.odometry.getHeading());
            telemetry.update();
        }
    }


}
