package org.firstinspires.ftc.teamcode.utilities.vision;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.utilities.Geometry;

import java.util.Collections;
import java.util.List;
import java.util.Objects;


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
        limelight.setPollRateHz(25); // Can be polled more often but expect the camera to heat up.
        limelight.pipelineSwitch(0);
        limelight.start();
    }

    public double findDistanceToBlock(){
        LLResult result = limelight.getLatestResult();
        List<LLResultTypes.ColorResult> colorResults = result.getColorResults();
        double distance = 0;

        for(LLResultTypes.ColorResult colorResult : colorResults){
            distance = colorResult.getTargetYDegrees();
        }

        return distance;
    }

    public double findStrafeToBlock(){
        LLResult result = limelight.getLatestResult();
        List<LLResultTypes.ColorResult> colorResults = result.getColorResults();
        double distance = 0;

        for(LLResultTypes.ColorResult colorResult : colorResults){
            distance = colorResult.getTargetXDegrees();
        }

        return distance;
    }

    /** Finds angle of block relative to limelight (in degrees). */
    public double findBlockAngle(int color) {
        //limelight.pipelineSwitch(color);
        LLResult result = limelight.getLatestResult();
        if(result == null) return -1.0;

        // Block pipelines inhabit the first 3 indexes on the limelight, hence why this works.
        if (result.getPipelineIndex() > Pipeline.RED_BLOCKS) return 0.0;

        List<LLResultTypes.ColorResult> colorResults = result.getColorResults();
        List<List<Double>> corners = Collections.emptyList();

        for (LLResultTypes.ColorResult colorResult : colorResults) {
            // FIXME?: Will only return last colorResult from list. I am unsure if this is intended.
            corners = colorResult.getTargetCorners();
        }

        Geometry.Vector2D corner1 = null;
        Geometry.Vector2D corner2 = null;
        Geometry.Vector2D corner3 = null;

       if(corners.size() >= 3){
           corner1 = getVectorFromList(corners.get(0));
           corner2 = getVectorFromList(corners.get(1));
           corner3 = getVectorFromList(corners.get(2));
       } else {
           return -1.0;
       }

        double l1squared = Geometry.getLengthSquared(corner1, corner2);
        double l2squared = Geometry.getLengthSquared(corner2, corner3);

        if(l1squared > l2squared){
            return Math.toDegrees(Geometry.getAngle(corner1, corner2));
        } else {
            return Math.toDegrees(Geometry.getAngle(corner2, corner3));
        }
    }

    public Pose2D getFieldPosition(Intake intake) {
        limelight.pipelineSwitch(3);
        // Value returned if conditions are not ideal for gathering field position.
        final Pose2D badValue = new Pose2D(DistanceUnit.MM, 0.0, 0.0,AngleUnit.DEGREES,0.0);

        LLResult result = limelight.getLatestResult();

        if (result != null)
            if (result.getPipelineIndex() != Pipeline.APRIL_TAGS
                    || intake.leftSlide.servo.getPosition() != Intake.SlidePosition.RETRACTED)
                return badValue;

        List<LLResultTypes.FiducialResult> fiducialResults = result.getFiducialResults();
        Pose3D LLBotPos = null;
        double tagSize = 0;
        double tagAngle = 0;
        Pose3D botPos = result.getBotpose_MT2();
        for (LLResultTypes.FiducialResult fiducialResult : fiducialResults) {
            // FIXME?: Will only return last colorResult from list. I am unsure if this is intended.
            tagAngle = fiducialResult.getTargetXDegrees();
            tagSize = fiducialResult.getTargetArea();
            LLBotPos = fiducialResult.getRobotPoseFieldSpace();
        }

        assert LLBotPos != null;
        if(tagSize > .4 || (tagAngle > 10 && tagAngle < -10)){
            return new Pose2D(DistanceUnit.INCH, LLBotPos.getPosition().x, LLBotPos.getPosition().y, AngleUnit.DEGREES, LLBotPos.getOrientation().getYaw());
        }

        return badValue;
    }


    private Geometry.Vector2D getVectorFromList(List<Double> cornerList) {
        return new Geometry.Vector2D(cornerList.get(0), cornerList.get(1));
    }

}



