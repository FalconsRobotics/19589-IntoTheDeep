package org.firstinspires.ftc.teamcode.opmodes.tests.vision;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.teamcode.utilities.SubsystemsCollection;

@TeleOp(name = "Localization Test")
public class LocalizationTest extends LinearOpMode {
    public void runOpMode() throws InterruptedException{
        SubsystemsCollection.deinit();

        Limelight3A ll = hardwareMap.get(Limelight3A.class, "limelight");
        SubsystemsCollection sys = SubsystemsCollection.getInstance(hardwareMap);

        ll.pipelineSwitch(0);
        ll.setPollRateHz(30);
        ll.start();

        LLResult result = ll.getLatestResult();

        double robotYaw = sys.driveBase.odometry.getHeading();
        ll.updateRobotOrientation(robotYaw);
        if (result != null && result.isValid()) {
            Pose3D botpose_mt2 = result.getBotpose_MT2();
            if (botpose_mt2 != null) {
                double x = botpose_mt2.getPosition().x;
                double y = botpose_mt2.getPosition().y;
                telemetry.addData("MT2 Location:", "(" + x + ", " + y + ")");
            }
        }
    }
}
