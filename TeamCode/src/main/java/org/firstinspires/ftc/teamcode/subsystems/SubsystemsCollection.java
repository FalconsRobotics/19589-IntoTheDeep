package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;

import java.security.InvalidParameterException;

/** Collection of subsystems and associated procedures intended to be used by all OpModes. */
public class SubsystemsCollection {
    private static SubsystemsCollection instance = null;
    private HardwareMap map = null; // Also tracks if instance was already initialized.

    // All subsystems
    public final DriveBase driveBase;
    public final Intake intake;
    public final Extake extake;

    private SubsystemsCollection(HardwareMap hMap) {
        map = hMap;

        driveBase = new DriveBase(map);
        intake = new Intake(map);
        extake = new Extake(map);
    }

    public static SubsystemsCollection getInstance(HardwareMap hMap) {
        if (instance == null) {
            if (hMap == null) {
                throw new InvalidParameterException();
            }

            instance = new SubsystemsCollection(hMap);
        }

        return instance;
    }

    public void periodic() {
        driveBase.periodic();
        intake.periodic();
    }
}
