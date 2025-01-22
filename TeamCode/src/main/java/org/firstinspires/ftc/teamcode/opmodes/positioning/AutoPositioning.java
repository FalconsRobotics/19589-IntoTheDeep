package org.firstinspires.ftc.teamcode.opmodes.positioning;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.utilities.SubsystemsCollection;
import org.firstinspires.ftc.teamcode.utilities.roadrunner.OdometryLimelightLocalizer;


@TeleOp(name = "Positioning - Auto", group = "Positioning")
public class AutoPositioning extends LinearOpMode {
    public void runOpMode() {
        SubsystemsCollection.deinit();
        SubsystemsCollection sys = SubsystemsCollection.getInstance(hardwareMap);
        sys.driveBase.driveRobotCentric = true;

        OdometryLimelightLocalizer localizer = new OdometryLimelightLocalizer(hardwareMap);

        localizer.setPoseEstimate(new Pose2d(0.0, 0.0, Math.toRadians(180)));
        waitForStart();

        while (opModeIsActive()) {
            sys.periodic();
            localizer.update();

            sys.driveBase.motorPowers = new Pose2d(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);
            Pose2d pose = localizer.getPoseEstimate();

            telemetry.addData("X", pose.getX());
            telemetry.addData("Y", pose.getY());
            telemetry.addData("Heading", Math.toDegrees(pose.getHeading()));
            telemetry.update();
        }
    }
}
