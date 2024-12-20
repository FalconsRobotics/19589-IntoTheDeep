package org.firstinspires.ftc.teamcode.utilities.roadrunner;

import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;

import java.util.List;

public class VisionControlUtility {

     public static Limelight3A limelight;
     static List<List<Double>> corners;
     public VisionControlUtility(HardwareMap map) {
          limelight = map.get(Limelight3A.class, "limelight");
          limelight.pipelineSwitch(0);
     }


    public static double findLength(double x1, double y1, double x2, double y2) {
          return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
     }

     public static double findAngle(double x1, double y1, double x2, double y2) {
          return Math.toDegrees(Math.atan2(y2 - y1, x2 - x1));
     }

     public double processCorners(){
         corners = limelight.getLatestResult().getColorResults().get(0).getTargetCorners();
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



