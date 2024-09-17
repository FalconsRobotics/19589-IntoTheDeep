package org.firstinspires.ftc.teamcode.common;

import androidx.annotation.GuardedBy;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.ftc.LazyImu;
import com.arcrobotics.ftclib.command.Subsystem;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.configuration.LynxConstants;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.teamcode.common.drive.pathing.geometry.Vector2D;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

@Config
public class RobotHardware {
    private static RobotHardware instance = null;

    private boolean enabled;
    private HardwareMap hardwareMap;
    public LynxModule CONTROL_HUB;
    public List<LynxModule> modules;

    ///region Hardware Objects
    public DcMotorEx leftFront, leftBack, rightBack, rightFront;

    ///endregion

    private final Object imuLock = new Object();
    @GuardedBy("imuLock")
    public IMU imu;
    private Thread imuThread;
    private double imuAngle = 0;
    public double imuOffset = 0;
    private double startOffset = 0;
    private double voltage = 12.0;

    public HashMap<Sensors.SensorType, Object> values;
    public List<Subsystem> subsystems;

    public static RobotHardware getInstance(){
        if (instance == null){
            instance = new RobotHardware();
            instance.enabled = true;
        }
        return instance;
    }

    protected RobotHardware(){
        enabled = false;
    }

    public void init(final HardwareMap hMap){
        hardwareMap = hMap;

        this.values = new HashMap<>();
        subsystems = new ArrayList<Subsystem>();

//        values.put(Sensors.SensorType.EXTENSION_ENCODER, 0);
//        values.put(Sensors.SensorType.ARM_ENCODER, 0.0);
//        values.put(Sensors.SensorType.POD_LEFT, 0.0);
//        values.put(Sensors.SensorType.POD_FRONT, 0.0);
//        values.put(Sensors.SensorType.POD_RIGHT, 0.0);

        leftFront = hardwareMap.get(DcMotorEx.class, "leftFront");
        leftBack = hardwareMap.get(DcMotorEx.class, "leftBack");
        rightBack = hardwareMap.get(DcMotorEx.class, "rightBack");
        rightFront = hardwareMap.get(DcMotorEx.class, "rightFront");

        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        // TODO: reverse motor directions if needed
        //   leftFront.setDirection(DcMotorSimple.Direction.REVERSE);

        modules = hardwareMap.getAll(LynxModule.class);
        for (LynxModule m : modules) {
            m.setBulkCachingMode(LynxModule.BulkCachingMode.MANUAL);
            if (m.isParent() && LynxConstants.isEmbeddedSerialNumber(m.getSerialNumber())) CONTROL_HUB = m;
        }

        ///TODO: Need to update to match our robot configuration
        // Initialize IMU directly
        imu.initialize(
                new IMU.Parameters(
                        new RevHubOrientationOnRobot(
                                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                                RevHubOrientationOnRobot.UsbFacingDirection.FORWARD
                        )
                )
        );

        voltage = hardwareMap.voltageSensor.iterator().next().getVoltage();
    }

    public void periodic(){
        for(Subsystem subsystem : subsystems){
            subsystem.periodic();
        }
    }

    public void AddSubsystems(Subsystem subsystem){
        subsystems.add(subsystem);
    }

    public void startIMUThread(LinearOpMode opMode) {
        imuThread = new Thread(() -> {
            while (!opMode.isStopRequested()) {
                synchronized (imuLock) {
                    imuAngle = AngleUnit.normalizeRadians(imu.getRobotOrientation(AxesReference.INTRINSIC,
                            AxesOrder.XYZ,
                            AngleUnit.RADIANS).firstAngle);
                }
            }
        });
        imuThread.start();
    }

    public void readIMU() {
        imuAngle = AngleUnit.normalizeRadians(imu.getRobotOrientation(AxesReference.INTRINSIC,
                AxesOrder.XYZ,
                AngleUnit.RADIANS).firstAngle);
    }

    public double getAngle() {
        return AngleUnit.normalizeRadians(imuAngle - imuOffset + startOffset);
    }

    public double getVoltage() {
        return voltage;
    }

}
