package org.firstinspires.ftc.teamcode.utilities.vision;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;

import java.util.List;

public class BlockLocatorPipeline extends Pipeline {

    public BlockLocatorPipeline(int id, Limelight3A limelight) {
        super(id, limelight); // TODO: Find IDs in limelight!
    }

    public boolean periodic() {
        LLResult latest = limelight.getLatestResult();
        List<LLResultTypes.ColorResult> results = latest.getColorResults();
        if (results.isEmpty()) return false; // else:

        // Pipeline is assumed to sort color results starting from the closest result.
        LLResultTypes.ColorResult closest = results.get(0);
        suggestedAction = closest.getTargetPoseRobotSpace(); // maybe camera space would be better?\

        if (suggestedAction != null) return true;

        // TODO: Update this to ignore noise. Or not!
        return true;
    }
}
