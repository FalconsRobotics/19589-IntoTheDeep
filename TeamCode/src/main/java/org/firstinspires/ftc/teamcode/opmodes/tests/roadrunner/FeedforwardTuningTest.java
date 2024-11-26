package org.firstinspires.ftc.teamcode.opmodes.tests.roadrunner;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.utilities.SubsystemsCollection;
import org.firstinspires.ftc.teamcode.utilities.ControlConstants;
import org.firstinspires.ftc.teamcode.utilities.roadrunner.AutoDriveUtility;

@Config
@TeleOp(name = "Test - Road Runner - Feedforward Tuning", group = "Road Runner")
public class FeedforwardTuningTest extends LinearOpMode {
    public static double SPEED_INCREMENT = 0.001;

    public void runOpMode() {
        double speed = ControlConstants.RoadRunner.KS;

        SubsystemsCollection.deinit();
        SubsystemsCollection sys = SubsystemsCollection.getInstance(hardwareMap);

        sys.driveBase.brake(true);
        AutoDriveUtility driveUtil = new AutoDriveUtility(sys.driveBase);

        while (opModeInInit()) {
            speed += SPEED_INCREMENT * -gamepad1.right_stick_y;

            if (gamepad1.a) speed = 0.0;
            else if (gamepad1.x) speed = ControlConstants.RoadRunner.KV;
            else if (gamepad1.y) speed = ControlConstants.RoadRunner.KA;
            else if (gamepad1.b) speed = ControlConstants.RoadRunner.KS;

            telemetry.addData("Wheel Speed", speed);
            telemetry.update();
        }

        waitForStart();

        while (opModeIsActive()) {
            speed += SPEED_INCREMENT * -gamepad1.right_stick_y;

            sys.driveBase.motors.driveWithMotorPowers(speed, speed, speed, speed);
            if (gamepad1.left_bumper || gamepad1.right_bumper) {
                sys.driveBase.motors.driveRobotCentric(
                        gamepad1.left_stick_x * 0.5, -gamepad1.left_stick_y * 0.5, gamepad1.right_stick_x * 0.5
                );
            }

            driveUtil.printPoseEstimate(telemetry);
            telemetry.addData("Wheel Speed", speed);
            telemetry.update();

            sys.periodic();
            driveUtil.mecanumDrive.updatePoseEstimate();
            // driveUtil.periodic() would send drive signals to drive base. NOT GOOD HERE!
        }
    }
}
