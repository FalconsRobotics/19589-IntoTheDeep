package org.firstinspires.ftc.teamcode.utilities.roadrunner;

import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.utilities.SubsystemsCollection;

import java.util.List;

public class VisionControlUtility {

     private static VisionControlUtility instance = null;
     private HardwareMap map = null;

     Limelight3A limelight;

     private VisionControlUtility(HardwareMap map) {
          this.map = map;

          limelight = map.get(Limelight3A.class, "limelight");
     }


     //List<double[]> corners = limelight.targetCorners();
     double x1, y1;
     double x2, y2;
     double x3, y3;

     public double findLength(double x1, double y1, double x2, double y2) {
          return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
     }

     double l1 = findLength(x1, y1, x2, y2);
     double l2 = findLength(x2, y2, x3, y3);

     public double findAngle(double x1, double y1, double x2, double y2) {
          return Math.toDegrees(Math.atan2(y2 - y1, x2 - x1));
     }

     public double returnAngle() {
          if (l1 > l2) {
               findAngle(x1, y1, x2, y2);
          } else {
               findAngle(x2, y2, x3, y3);
          }

          return 0.0;
     }
}



