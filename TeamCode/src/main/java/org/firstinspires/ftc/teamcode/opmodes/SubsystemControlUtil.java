package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.subsystems.DriveBase;

/** Collection of subsystems and associated procedures intended to be used by all OpModes. */
public class SubsystemControlUtil {
    public DriveBase driveBase;

    public void SubsystemControlUtil(HardwareMap map) {
        driveBase = new DriveBase(map);
    }

    public void finalizeAll() {
        driveBase.finalize();
    }
}
