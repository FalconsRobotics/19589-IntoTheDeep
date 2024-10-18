
package org.firstinspires.ftc.teamcode.opmodes.tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.DriveBase;

import org.firstinspires.ftc.teamcode.opmodes.util.GamepadWrapper;


@TeleOp
public class DriveBaseTest extends LinearOpMode {
    public void runOpMode() {
        final DriveBase driveBase = new DriveBase(hardwareMap);
        final GamepadWrapper pad = new GamepadWrapper(gamepad1);

        boolean movementModeRobotCentric = true;

        waitForStart();

        while (opModeIsActive()) {
            driveBase.update();
            pad.update();

            if (pad.a.justPressed()) {
                movementModeRobotCentric = true;
            } else if (pad.b.justPressed()) {
                movementModeRobotCentric = false;
            }

            if (movementModeRobotCentric) {
                driveBase.setVelocity(-pad.leftStick.getY(),
                        pad.leftStick.getX(),
                        pad.rightStick.getX());
            } else {
                driveBase.setVelocityFieldCentric(-pad.leftStick.getY(),
                        pad.leftStick.getX(),
                        pad.rightStick.getX());
            }
        }

        driveBase.finalize();
    }
}
