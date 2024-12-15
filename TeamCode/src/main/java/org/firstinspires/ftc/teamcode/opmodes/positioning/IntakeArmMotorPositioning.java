package org.firstinspires.ftc.teamcode.opmodes.positioning;

import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.utilities.SubsystemsCollection;


@TeleOp(name = "Positioning - Intake Arm", group = "Positioning")
public class IntakeArmMotorPositioning extends LinearOpMode {
    public void runOpMode() {
        SubsystemsCollection.deinit();
        SubsystemsCollection sys = SubsystemsCollection.getInstance(hardwareMap);
        sys.intake.arm.motor.setZeroPowerBehavior(Motor.ZeroPowerBehavior.FLOAT);

        waitForStart();

        while (opModeIsActive()) {
            sys.intake.arm.motor.set(0);

            telemetry.addData("Position", sys.intake.arm.motor.getCurrentPosition());
            telemetry.update();
            // No periodic to avoid killing our robot!
        }
    }
}
