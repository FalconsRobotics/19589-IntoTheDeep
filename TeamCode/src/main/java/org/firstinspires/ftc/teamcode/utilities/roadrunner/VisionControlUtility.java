package org.firstinspires.ftc.teamcode.utilities.roadrunner;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;

import java.util.List;

public class VisionControlUtility {

     public Limelight3A limelight;
     List<List<Double>> corners;
     public VisionControlUtility(HardwareMap map) {
          limelight = hardwareMap.get(Limelight3A.class, "limelight");
          telemetry.setMsTransmissionInterval(11);
     }


    public double findLength(double x1, double y1, double x2, double y2) {
          return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
     }

     public double findAngle(double x1, double y1, double x2, double y2) {
          return Math.toDegrees(Math.atan2(y2 - y1, x2 - x1));
     }

     public double processCorners(){
         limelight.start();
         LLResult result = limelight.getLatestResult();
         List<LLResultTypes.ColorResult> colorTargets = result.getColorResults();
         for (LLResultTypes.ColorResult colorTarget : colorTargets) {
             corners = colorTarget.getTargetCorners();
         }
         List<Double> corner1 = corners.get(0);
         List<Double> corner2 = corners.get(1);
         List<Double> corner3 = corners.get(2);

          double l1 = findLength(corner1.get(0), corner1.get(1), corner2.get(0), corner2.get(1));
          double l2 = findLength(corner2.get(0), corner2.get(1), corner3.get(0), corner3.get(1));

          if(l1 > l2){
              return findAngle(corner1.get(0), corner1.get(1), corner2.get(0), corner2.get(1));
          } else {
              return findAngle(corner2.get(0), corner2.get(1), corner3.get(0), corner3.get(1));
          }

     }

}



