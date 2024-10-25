package org.firstinspires.ftc.teamcode.opmodes.tests;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.subsystems.DriveBase;
import org.firstinspires.ftc.teamcode.subsystems.SubsystemsCollection;

@TeleOp(name = "Test - Drive Base - Simple")
public class DriveBaseSimple extends LinearOpMode {
    public void runOpMode() {
        SubsystemsCollection sys = SubsystemsCollection.getInstance(hardwareMap);

        DcMotor frontLeft = hardwareMap.dcMotor.get("DriveBase-FrontLeft");
        DcMotor backLeft = hardwareMap.dcMotor.get("DriveBase-BackLeft");
        DcMotor frontRight = hardwareMap.dcMotor.get("DriveBase-FrontRight");
        DcMotor backRight = hardwareMap.dcMotor.get("DriveBase-BackRight");

        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();

        while (opModeIsActive()) {
            sys.periodic();
            sys.driveBase.motors.driveRobotCentric(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);
        }


    }
}
