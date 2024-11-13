package org.firstinspires.ftc.teamcode.opmodes.tests.roadrunner;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.utilities.SubsystemsCollection;
import org.firstinspires.ftc.teamcode.utilities.Constants;
import org.firstinspires.ftc.teamcode.utilities.roadrunner.RRDriveUtility;

@Config
@TeleOp(name = "Test - Road Runner - Feedforward Tuning", group = "Road Runner")
public class FeedforwardTuningTest extends LinearOpMode {
    public static double SPEED_INCREMENT = 0.001;

    public void runOpMode() {
        double speed = Constants.RoadRunner.KS;

        SubsystemsCollection.deinit();
        SubsystemsCollection sys = SubsystemsCollection.getInstance(hardwareMap);

        sys.driveBase.brake(true);
        RRDriveUtility driveUtil = new RRDriveUtility(sys.driveBase);

        waitForStart();

        while (opModeIsActive()) {
            speed += SPEED_INCREMENT * -gamepad1.left_stick_y;

            if (gamepad1.a) {
                speed = 0.0;
            }

            sys.driveBase.motors.driveWithMotorPowers(speed, speed, speed, speed);

            driveUtil.printPoseEstimate(telemetry);
            telemetry.addData("Wheel Speed", speed);
            telemetry.update();

            sys.periodic();
            driveUtil.mecanumDrive.updatePoseEstimate();
            // driveUtil.periodic() would send drive signals to drive base. NOT GOOD HERE!
        }
    }
}
