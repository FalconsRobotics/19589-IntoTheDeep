package org.firstinspires.ftc.teamcode.opmodes.util;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.subsystems.Subsystem;
import org.firstinspires.ftc.teamcode.subsystems.DriveBase;

/** Collection of subsystems and associated procedures intended to be used by all OpModes. */
public class SubsystemsCollection implements Subsystem {
    public DriveBase driveBase;

    public SubsystemsCollection(HardwareMap map) {
        driveBase = new DriveBase(map);
    }

    public void update() {
        driveBase.update();
    }

    public void finalize() {
        driveBase.finalize();
    }
}
