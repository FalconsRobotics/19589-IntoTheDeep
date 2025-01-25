package com.meepmeeptesting;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;

import org.rowlandhall.meepmeep.MeepMeep;
import org.rowlandhall.meepmeep.roadrunner.DefaultBotBuilder;
import org.rowlandhall.meepmeep.roadrunner.DriveTrainType;
import org.rowlandhall.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepSpecimenAuto {
    public static void main(String[] args) {
        System.setProperty("sun.java2d.opengl", "true");

        Pose2d startPos = new Pose2d(9, -63, Math.toRadians(0));

        MeepMeep meepMeep = new MeepMeep(900);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(40, 40, Math.toRadians(180), Math.toRadians(180), 18.46)
                .setDimensions(12.75, 17.75)
                .setDriveTrainType(DriveTrainType.MECANUM)
                .followTrajectorySequence(drive -> drive.trajectorySequenceBuilder(startPos)
                        .lineToConstantHeading(new Vector2d(-1, -30))
                        .splineToLinearHeading(new Pose2d(24, -48, Math.toRadians(45)), Math.toRadians(0))

                        .build());


        meepMeep.setBackground(MeepMeep.Background.FIELD_INTOTHEDEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}