package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.subsystems.DriveBase;

/** Collection of subsystems and associated procedures intended to be used by all OpModes. */
public class SubsystemControlUtil {
    public final DriveBase driveBase;

    public void initAll(HardwareMap map) {
        driveBase = new DriveBase(map);
    }

    public void finalizeAll() {
        driveBase.finalize();
    }
}
