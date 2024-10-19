package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;

import java.security.InvalidParameterException;

/** Collection of subsystems and associated procedures intended to be used by all OpModes. */
public class SubsystemsCollection {
    private static SubsystemsCollection instance = null;
    private HardwareMap map = null; // Also tracks if instance was already initialized.

    // All subsystems
    public final DriveBase driveBase;

    private SubsystemsCollection(HardwareMap hMap) {
        map = hMap;

        driveBase = new DriveBase(map);
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
}
