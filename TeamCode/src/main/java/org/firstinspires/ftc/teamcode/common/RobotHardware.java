package org.firstinspires.ftc.teamcode.common;

import androidx.annotation.GuardedBy;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.configuration.LynxConstants;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.common.drive.pathing.geometry.Vector2D;

import java.util.HashMap;
import java.util.List;

@Config
public class RobotHardware {
    private static RobotHardware instance = null;

    private boolean enabled;
    private HardwareMap hardwareMap;
    public LynxModule CONTROL_HUB;
    public List<LynxModule> modules;

    private final Object imuLock = new Object();
    @GuardedBy("imuLock")
    public BNO055IMU imu;
    private Thread imuThread;
    private double imuAngle = 0;
    public double imuOffset = 0;
    private double startOffset = 0;
    private double voltage = 12.0;

    public HashMap<Sensors.SensorType, Object> values;

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

        values.put(Sensors.SensorType.EXTENSION_ENCODER, 0);
        values.put(Sensors.SensorType.ARM_ENCODER, 0.0);
        values.put(Sensors.SensorType.POD_LEFT, 0.0);
        values.put(Sensors.SensorType.POD_FRONT, 0.0);
        values.put(Sensors.SensorType.POD_RIGHT, 0.0);

        modules = hardwareMap.getAll(LynxModule.class);
        for (LynxModule m : modules) {
            m.setBulkCachingMode(LynxModule.BulkCachingMode.MANUAL);
            if (m.isParent() && LynxConstants.isEmbeddedSerialNumber(m.getSerialNumber())) CONTROL_HUB = m;
        }

        voltage = hardwareMap.voltageSensor.iterator().next().getVoltage();
    }

    public void periodic(){
        //intake.periodic();
        //extension.periodic();
        //drivetrain.periodic();
        if (Globals.IsAuto) {
            //localizer.periodic();
        }
    }

    public void AddSubsystems(){}

    public void startIMUThread(LinearOpMode opMode) {
        imuThread = new Thread(() -> {
            while (!opMode.isStopRequested()) {
                synchronized (imuLock) {
                    imuAngle = AngleUnit.normalizeRadians(imu.getAngularOrientation().firstAngle);
                }
            }
        });
        imuThread.start();
    }

    public void readIMU() {
        imuAngle = AngleUnit.normalizeRadians(imu.getAngularOrientation().firstAngle);
    }

    public double getAngle() {
        return AngleUnit.normalizeRadians(imuAngle - imuOffset + startOffset);
    }

    public double getVoltage() {
        return voltage;
    }

}
