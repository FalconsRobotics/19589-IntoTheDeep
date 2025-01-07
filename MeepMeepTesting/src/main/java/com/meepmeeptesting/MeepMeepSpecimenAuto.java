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

        Pose2d startPos = new Pose2d(48, -24, Math.toRadians(180));

        MeepMeep meepMeep = new MeepMeep(900);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(90, 90, Math.toRadians(180), Math.toRadians(180), 18.46)
                .setDimensions(12.75, 17.75)
                .setDriveTrainType(DriveTrainType.MECANUM)
                .followTrajectorySequence(drive -> drive.trajectorySequenceBuilder(startPos)
                        /// Move to bar and place pre-loaded specimen
//                        .lineToConstantHeading(new Vector2d(1, -30))
//
//                        /// Move and prepare for dragging all three pieces to human player
//                        // First piece
//                        .back(24)
//                        .splineToConstantHeading(new Pose2d(48, -8, Math.toRadians(0)).vec(), 0)
//                        .lineToConstantHeading(new Vector2d(48, -56))
//
//                        // Second piece
//                        .lineToConstantHeading(new Vector2d(48, -24))
//                        .splineToConstantHeading(new Vector2d(58, -8), Math.toRadians(0))
//                        .lineToConstantHeading(new Vector2d(58, -56))
//
//                        // Third piece
//                        .lineToConstantHeading(new Vector2d(58, -24))
//                        .splineToConstantHeading(new Vector2d(64, -8), Math.toRadians(0))
//                        .lineToConstantHeading(new Vector2d(64, -56))
//
//                        .lineToConstantHeading(new Vector2d(48 - 4.4375, -48))
//                        .strafeLeft(24)

                        /// And the star of the show... cycle!
                        .splineToLinearHeading(new Pose2d(60, -63.625, Math.toRadians(0)), Math.toRadians(270))
                        .lineToLinearHeading(new Pose2d(1, -30, Math.toRadians(180)))

                        .splineToConstantHeading(new Vector2d(60, -63.625), Math.toRadians(0 + 1e+6))

                        .build());


        meepMeep.setBackground(MeepMeep.Background.FIELD_INTOTHEDEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}