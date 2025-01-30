package com.meepmeeptesting.meepmeepsample;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;

import org.rowlandhall.meepmeep.MeepMeep;
import org.rowlandhall.meepmeep.roadrunner.DefaultBotBuilder;
import org.rowlandhall.meepmeep.roadrunner.DriveTrainType;
import org.rowlandhall.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepSampleAuto {
    public static void main(String[] args) {
        System.setProperty("sun.java2d.opengl", "true");

        MeepMeep meepMeep = new MeepMeep(600);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(37, 37, Math.toRadians(180), Math.toRadians(180), 18.46)
                .setDimensions(12.5, 17.75)
                .setDriveTrainType(DriveTrainType.MECANUM)
                .followTrajectorySequence(drive -> drive.trajectorySequenceBuilder(new Pose2d(-16, -12, Math.toRadians(0)))
                // .followTrajectorySequence(drive -> drive.trajectorySequenceBuilder(new Pose2d(-15.125, -63.125, Math.toRadians(180)))
                        //.lineToConstantHeading(new Vector2d(-14, -29)) /* Specimen */
                        //.strafeRight(10)
                        //.lineToLinearHeading(new Pose2d(-57, -55, Math.toRadians(45))) /* Bucket */
                        //.splineToLinearHeading(new Pose2d(-35.8, -33.4, Math.toRadians(145)), 3) /* Sample 1 */
                        //.lineToLinearHeading(new Pose2d(-57, -55, Math.toRadians(45))) /* Bucket */
                        //.lineToLinearHeading(new Pose2d(-58, -33, Math.toRadians(90))) /* Sample 2 */
                        //.lineToLinearHeading(new Pose2d(-57, -55, Math.toRadians(45))) /* Bucket */
                        //.splineToLinearHeading(new Pose2d(-60, -24, Math.toRadians(180)), 9)
                        // .lineToLinearHeading(new Pose2d(-16, -12, Math.toRadians(0)))
                        .back(20)
                        .lineToLinearHeading(new Pose2d(-54.25, -54.25, Math.toRadians(45)))
                        .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTOTHEDEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}