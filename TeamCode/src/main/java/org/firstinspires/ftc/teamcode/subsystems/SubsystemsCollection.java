package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;

import java.security.InvalidParameterException;

/** Collection of subsystems and associated procedures intended to be used by all OpModes. */
public class SubsystemsCollection {
    private static SubsystemsCollection instance = null;
    private HardwareMap map = null; // Also tracks if instance was already initialized.

    /** Drive-base subsystem. */
    public final DriveBase driveBase;
    /** Manages all systems related to intake. */
    public final Intake intake;
    /** Manages all system related to extake. */
    public final Extake extake;

    private SubsystemsCollection(HardwareMap map) {
        this.map = map;

        driveBase = new DriveBase(this.map);
        intake = new Intake(this.map);
        extake = new Extake(this.map);
    }

    /** Returns singleton instance of SubsystemsCollection. If this instance does not yet exist, it
     *  will be created. If this is the case `hMap` must be supplied an appropriate object,
     *  otherwise it may be null. */
    public static SubsystemsCollection getInstance(HardwareMap map) {
        if (instance == null) {
            if (map == null) {
                throw new InvalidParameterException();
            }

            instance = new SubsystemsCollection(map);
        }

        return instance;
    }

    public void periodic() {
        driveBase.periodic();
        intake.periodic();
        extake.periodic();
    }

    public static void deinit() {
        instance = null;
    }
}
