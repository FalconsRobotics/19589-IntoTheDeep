package org.firstinspires.ftc.teamcode.opmodes.tests.roadrunner;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.external.GoBildaPinpointDriver;
import org.firstinspires.ftc.teamcode.utilities.SubsystemsCollection;
import org.firstinspires.ftc.teamcode.utilities.roadrunner.RRDriveUtility;

@TeleOp(name = "Test - Road Runner - Localization", group = "Road Runner")
public class LocalizationTest extends LinearOpMode {
    public void runOpMode() {
        SubsystemsCollection.deinit();
        SubsystemsCollection sys = SubsystemsCollection.getInstance(hardwareMap);

        sys.driveBase.brake(true);
        RRDriveUtility driveUtil = new RRDriveUtility(sys.driveBase);

        waitForStart();

        while (opModeIsActive()) {
            if (sys.driveBase.odometry.getDeviceStatus() == GoBildaPinpointDriver.DeviceStatus.READY) {
                sys.driveBase.motors.driveRobotCentric(
                        gamepad1.left_stick_x * 0.5, -gamepad1.left_stick_y * 0.5, gamepad1.right_stick_x * 0.5
                );
            }

            if (gamepad1.a) sys.driveBase.odometry.recalibrateIMU();
            if (gamepad1.b) sys.driveBase.odometry.resetPosAndIMU();


            driveUtil.printPoseEstimate(telemetry);
            telemetry.addLine("== Odometry Module Data ==");
            telemetry.addData("X (mm)", sys.driveBase.odometry.getPosX());
            telemetry.addData("Y (mm)", sys.driveBase.odometry.getPosY());
            telemetry.addData("Heading (deg)", sys.driveBase.odometry.getHeading() * (180 / Math.PI));
            telemetry.update();

            sys.periodic();
            driveUtil.mecanumDrive.updatePoseEstimate();
            // driveUtil.periodic() would send drive signals to drive base. NOT GOOD HERE!
        }
    }
}
