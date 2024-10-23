package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.drivebase.MecanumDrive;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;

import com.arcrobotics.ftclib.command.SubsystemBase;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.ext.GoBildaPinpointDriver;


/** Manages all mechanisms associated with robot drive base. */
public class DriveBase extends SubsystemBase {
    public final MecanumDrive motors;
    public final GoBildaPinpointDriver odometry;
    public final Limelight3A limelight;

    public DriveBase(HardwareMap map) {
        Motor frontLeft = new Motor(map, "DriveBase-FrontLeft");
        Motor frontRight = new Motor(map, "DriveBase-FrontRight");
        Motor backLeft = new Motor(map, "DriveBase-BackLeft");
        Motor backRight = new Motor(map, "DriveBase-BackRight");

        motors = new MecanumDrive(true, frontLeft, frontRight, backLeft, backRight);

        odometry = map.get(GoBildaPinpointDriver.class, "DriveBase-Odometry");
        odometry.setEncoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        odometry.setEncoderDirections(GoBildaPinpointDriver.EncoderDirection.FORWARD, GoBildaPinpointDriver.EncoderDirection.FORWARD);
        odometry.setOffsets(-75.5, 83.2);

        odometry.recalibrateIMU();
        odometry.resetPosAndIMU();

        limelight = map.get(Limelight3A.class, "Limelight");
        limelight.setPollRateHz(30);
        limelight.start();
    }

    public void periodic() {
        odometry.update();
    }
}
