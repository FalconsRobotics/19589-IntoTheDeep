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

        Pose2d startPos = new Pose2d(9, -63, Math.toRadians(180));
//        Pose2d redSpecimenMiddle = new Pose2d(0, -30);

        Pose2d redPreplacedSample = new Pose2d(48, -24 + 10, Math.toRadians(90));

        MeepMeep meepMeep = new MeepMeep(900);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 18.46)
                .setDimensions(12.75, 17.75)
                .setDriveTrainType(DriveTrainType.MECANUM)
                .followTrajectorySequence(drive -> drive.trajectorySequenceBuilder(startPos)
                        /// Move to bar and place pre-loaded specimen
                        .lineToConstantHeading(new Vector2d(1, -30))

                        /// Move and prepare for dragging all three pieces to human player
                        // First piece
                        .lineToLinearHeading(new Pose2d(36, -48, Math.toRadians(90)))
                        .splineToConstantHeading(new Pose2d(48, -8, Math.toRadians(0)).vec(), 0)
                        .lineToConstantHeading(new Vector2d(48, -56))

                        // Second piece
                        .lineToConstantHeading(new Vector2d(48, -24))
                        .splineToConstantHeading(new Vector2d(58, -8), Math.toRadians(0))
                        .lineToConstantHeading(new Vector2d(58, -56))

                        // Third piece
                        .lineToConstantHeading(new Vector2d(58, -24))
                        .splineToConstantHeading(new Vector2d(64, -8), Math.toRadians(0))
                        .lineToConstantHeading(new Vector2d(64, -56))

                        /// Move out of the way so human player gets a chance to place the first specimen.
                        .lineToConstantHeading(new Vector2d(64, -46))
                        .waitSeconds(1)
                        .lineToLinearHeading(new Pose2d(43.5625, -63.625, Math.toRadians(180)))

                        /// And the star of the show... cycle!
                        // First cycle and back
                        .lineToLinearHeading(new Pose2d(-5, -30, Math.toRadians(0)))
                        .lineToLinearHeading(new Pose2d(43.5625, -63.625, Math.toRadians(0)))

                        // Second cycle and back
                        .lineToLinearHeading(new Pose2d(-3, -30, Math.toRadians(180)))
                        .lineToLinearHeading(new Pose2d(43.5625, -63.625, Math.toRadians(180)))

                        // Third cycle and back
                        .lineToLinearHeading(new Pose2d(-1, -30, Math.toRadians(0)))
                        .lineToLinearHeading(new Pose2d(43.5625, -63.625, Math.toRadians(0)))

                        // Fourth cycle and back
                        .lineToLinearHeading(new Pose2d(3, -30, Math.toRadians(180)))

                        // Park
                        .lineToLinearHeading(new Pose2d(43.5625, -63.625, Math.toRadians(180)))

                        .build());


        meepMeep.setBackground(MeepMeep.Background.FIELD_INTOTHEDEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}