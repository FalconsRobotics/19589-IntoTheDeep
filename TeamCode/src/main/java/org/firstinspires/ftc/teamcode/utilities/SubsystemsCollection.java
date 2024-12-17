package org.firstinspires.ftc.teamcode.utilities;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.subsystems.*;

import java.security.InvalidParameterException;

/** Collection of subsystems and associated procedures intended to be used by all OpModes. */
public class SubsystemsCollection {
    private static SubsystemsCollection instance = null;

    /** Robot subsystems. */
    public final DriveBase driveBase;
    public final Intake intake;
    public final Extake extake;
    public final Lift lift;

    private SubsystemsCollection(HardwareMap map) {
        driveBase = new DriveBase(map);
        intake = new Intake(map);
        extake = new Extake(map);
        lift = new Lift(map);
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

    /** Executes every command needed to be ran every cycle. Should not be called if you're using
     *  a command OpMode */
    public void periodic() {
        driveBase.periodic();
        intake.periodic();
        extake.periodic();
        // Question: How does FTCLib know to call these inside a CommandOpMode??????
    }

    /** Resets everything to be initialized once again. */
    public static void deinit() {
        instance = null;
    }
}
