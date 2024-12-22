package org.firstinspires.ftc.teamcode.utilities.vision;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.utilities.Geometry;

import java.util.List;


/** Utility class for managing everything to do with vision on the robot. */
public class VisionUtility {

    public static class Pipeline {
        public static final double YELLOW_BLOCKS = 0;
        public static final double BLUE_BLOCKS = 1;
        public static final double RED_BLOCKS = 2;
        public static final double APRIL_TAGS = 3;
    }

    /** For use outside of the utility object. Depending on how much this utility class does the
     *  user may never have to access this directly. */
    public Limelight3A limelight;


    public VisionUtility(HardwareMap map) {
        limelight = map.get(Limelight3A.class, "limelight");
        limelight.setPollRateHz(60); // Can be polled more often but expect the camera to heat up.
        limelight.start();
        limelight.pipelineSwitch(0);
    }


    /** Finds angle of block relative to limelight (in degrees). */
    public double findBlockAngle() {
        LLResult result = limelight.getLatestResult();

        // Block pipelines inhabit the first 3 indexes on the limelight, hence why this works.
        if (result.getPipelineIndex() > Pipeline.RED_BLOCKS) return 0.0;

        List<LLResultTypes.ColorResult> colorResults = result.getColorResults();
        List<List<Double>> corners = null;

        for (LLResultTypes.ColorResult colorResult : colorResults) {
            // FIXME?: Will only return last colorResult from list. I am unsure if this is intended.
            corners = colorResult.getTargetCorners();
        }

        Geometry.Vector2D corner1 = getCornerFromList(corners.get(0)); // may produce null?
        Geometry.Vector2D corner2 = getCornerFromList(corners.get(1));
        Geometry.Vector2D corner3 = getCornerFromList(corners.get(2));

        double l1squared = Geometry.getLengthSquared(corner1, corner2);
        double l2squared = Geometry.getLengthSquared(corner2, corner3);

        telemetry.addData("Line Length 1: ", Math.sqrt(l1squared));

        if(l1squared > l2squared){
            return Math.toDegrees(Geometry.getAngle(corner1, corner2));
        } else {
            return Math.toDegrees(Geometry.getAngle(corner2, corner3));
        }
    }


    private Geometry.Vector2D getCornerFromList(List<Double> cornerList) {
        return new Geometry.Vector2D(cornerList.get(0), cornerList.get(1));
    }

}



