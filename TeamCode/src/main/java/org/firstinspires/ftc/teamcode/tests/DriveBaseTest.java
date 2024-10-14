
package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.DriveBase;

@TeleOp
public class DriveBaseTest extends LinearOpMode {
    public void runOpMode() {
        final DriveBase driveBase = new DriveBase(hardwareMap);
        waitForStart();

        while (opModeIsActive() == true) {
            driveBase.setVelocity(gamepad1.left_stick_y, -gamepad1.left_stick_x, -gamepad1.right_stick_x, 1);
        }

        driveBase.finalize();
    }
}
