package com.meepmeeptesting;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;

import org.rowlandhall.meepmeep.MeepMeep;
import org.rowlandhall.meepmeep.roadrunner.DefaultBotBuilder;
import org.rowlandhall.meepmeep.roadrunner.DriveTrainType;
import org.rowlandhall.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(900);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 18.46)
                .setDimensions(12.75, 17.75)
                .setStartPose(new Pose2d(76, 0, Math.toRadians(90)))
                .setDriveTrainType(DriveTrainType.MECANUM)
                .followTrajectorySequence(drive -> drive.trajectorySequenceBuilder(new Pose2d(0, 0, 0))
                        .forward(9)
                        .strafeRight(34)
                        .splineToConstantHeading(new Vector2d(-32.25, -5.0), 0.0)
                        .strafeLeft(5)
                        .strafeRight(5)
                        .splineToLinearHeading(new Pose2d(10, -30, Math.toRadians(180)), Math.toRadians(0))
                        .strafeLeft(4)
                        .build());


        meepMeep.setBackground(MeepMeep.Background.FIELD_INTOTHEDEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}