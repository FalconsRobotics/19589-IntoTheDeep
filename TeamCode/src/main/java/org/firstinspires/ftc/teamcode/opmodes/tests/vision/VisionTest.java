package org.firstinspires.ftc.teamcode.opmodes.tests.vision;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.teamcode.utilities.SubsystemsCollection;
import org.firstinspires.ftc.teamcode.utilities.vision.BlockLocatorPipeline;
import org.firstinspires.ftc.teamcode.utilities.vision.VisionUtility;

import java.util.List;

@TeleOp(name="Limelight Test")
public class VisionTest extends LinearOpMode {


    public void runOpMode() throws InterruptedException {
        SubsystemsCollection.deinit();
        final SubsystemsCollection sys = SubsystemsCollection.getInstance(hardwareMap);

        final VisionUtility vision = new VisionUtility(hardwareMap);
        vision.switchPipelines(
                new BlockLocatorPipeline(VisionUtility.LimelightPipelineIndex.BLUE_BLOCKS,
                        vision.limelight)
        );

        waitForStart();

        while (opModeIsActive()) {
            sys.periodic();

            vision.limelight.start();

            if (gamepad1.a) vision.getPipeline().init();

            Pose3D action = vision.getSuggestedAction();
            if (action == null) continue;

            telemetry.addData("X: ", action.getPosition().x);
            telemetry.addData("Y: ", action.getPosition().y);
            telemetry.addData("Yaw: ", action.getOrientation().getYaw());
            telemetry.update();

            LLResult result = vision.limelight.getLatestResult();
            if (result != null && result.isValid()) {
                double tx = result.getTx(); // How far left or right the target is (degrees)
                double ty = result.getTy(); // How far up or down the target is (degrees)
                double ta = result.getTa(); // How big the target looks (0%-100% of the image)

                List<LLResultTypes.ColorResult> colorResults = result.getColorResults();
                for (LLResultTypes.ColorResult colorResult : colorResults) {
                    telemetry.addData("Target POS", colorResult.getTargetPoseCameraSpace());

                    telemetry.addData("Corner Count", colorResult.getTargetCorners().size());
                    int i = 0;
                    List<List<Double>> corners = colorResult.getTargetCorners();
                    List<Double> corner1 = corners.get(0);
                    List<Double> corner2 = corners.get(1);
                    for(List<Double> corner : colorResult.getTargetCorners()){
                        for(double c  : corner){
                            telemetry.addData("Corner " + i, c);
                            i++;
                        }
                    }

                    telemetry.addData("X pixels: ", colorResult.getTargetXPixels());
                    telemetry.addData("X degrees: ", colorResult.getTargetXDegrees());
                    telemetry.addData("Y pixels: ", colorResult.getTargetYPixels());
                    telemetry.addData("Y degrees: ", colorResult.getTargetYDegrees());

                    double angle = Math.toDegrees(Math.atan2(corner2.get(1) - corner1.get(1), corner2.get(0) - corner1.get(0)));

                    telemetry.addData("Target X", tx);
                    telemetry.addData("Target Y", ty);
                    telemetry.addData("Target Area", ta);
                    telemetry.addData("Target Angle:", angle);

                    telemetry.update();
                }
            } else {
                telemetry.addData("Limelight", "No Targets");
            }
            telemetry.update();
            sleep(500);
       }
   }
}
