package org.firstinspires.ftc.teamcode.utilities;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.VoltageSensor;

import java.security.InvalidParameterException;


public class PowerRegulator {
    private static PowerRegulator singleton = null;

    /** For getting current voltage from battery. */
    private final VoltageSensor voltageSensor;


    private PowerRegulator(HardwareMap map) {
        voltageSensor = map.get(VoltageSensor.class, "Control Hub");
    }


    public static PowerRegulator getInstance(HardwareMap map) {
        if (singleton == null) {
            if (map == null) {
                throw new InvalidParameterException();
            }

            singleton = new PowerRegulator(map);
        }

        return singleton;
    }

    public double getPowerMultiplier() {
        final double BATTERY_CHARGED_VOLTAGE = 12.0;
        return BATTERY_CHARGED_VOLTAGE / voltageSensor.getVoltage();
    }
}
