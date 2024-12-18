package org.firstinspires.ftc.teamcode.opmodes.tests;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import java.util.List;

@TeleOp(name="Limelight Test")
public class visionTest extends LinearOpMode {

    private Limelight3A limelight;

    public void runOpMode() throws InterruptedException {
        limelight = hardwareMap.get(Limelight3A.class, "limelight");

        telemetry.setMsTransmissionInterval(11);

        limelight.setPollRateHz(25);
        limelight.pipelineSwitch(0);

        /*
         * Starts polling for data.
         */
        limelight.start();
        telemetry.addLine("Robot Ready");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {

            LLResult result = limelight.getLatestResult();
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

                    telemetry.addData("Target X", tx);
                    telemetry.addData("Target Y", ty);
                    telemetry.addData("Target Area", ta);
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
