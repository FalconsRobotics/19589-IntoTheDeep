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

        DcMotor frontLeft = hardwareMap.dcMotor.get("DriveBase-Wheel0");
        DcMotor backLeft = hardwareMap.dcMotor.get("DriveBase-Wheel1");
        DcMotor frontRight = hardwareMap.dcMotor.get("DriveBase-Wheel2");
        DcMotor backRight = hardwareMap.dcMotor.get("DriveBase-Wheel3");

        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        frontRight.setDirection(DcMotorSimple.Direction.FORWARD);
        backRight.setDirection(DcMotorSimple.Direction.FORWARD);

        waitForStart();

        while (opModeIsActive()) {
            sys.driveBase.setVelocity(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);
        }


    }
}
