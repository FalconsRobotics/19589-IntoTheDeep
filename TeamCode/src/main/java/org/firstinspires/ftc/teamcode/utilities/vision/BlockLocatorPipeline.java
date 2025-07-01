package org.firstinspires.ftc.teamcode.utilities.vision;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class BlockLocatorPipeline extends Pipeline {

    public BlockLocatorPipeline(int id, HardwareMap map) {
        super(id, map); // TODO: Find IDs in limelight!
    }

    public void init() {
        super.init();

    }

    public boolean loop() {
        LLResult latest = limelight.getLatestResult();

        return false; // FIXME
    }
}
