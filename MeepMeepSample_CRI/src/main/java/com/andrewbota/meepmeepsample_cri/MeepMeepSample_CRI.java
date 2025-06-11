package com.andrewbota.meepmeepsample_cri;

import com.acmerobotics.roadrunner.Pose2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepSample_CRI {
    public static void main(String[] args) {
        System.setProperty("sun.java2d.opengl", "true");

        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .setDimensions(13, 17.5)
                .build();

        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(-48 + (13 / 2), -72 + (17.5 / 2), 0))
                // Bucket preload
                .splineToLinearHeading(new Pose2d(-56, -56, Math.toRadians(45)), Math.toRadians(90))
                // Pickup right ground
                .splineToLinearHeading(new Pose2d(-48, -24 - 17, Math.toRadians(90)), Math.toRadians(0))
                // Place right ground
                .splineToLinearHeading(new Pose2d(-56, -56, Math.toRadians(45)), Math.toRadians(45))
                // Pickup middle ground
                .splineToLinearHeading(new Pose2d(-58, -24 - 17, Math.toRadians(90)), Math.toRadians(90))
                // Place middle ground
                .splineToLinearHeading(new Pose2d(-56, -56, Math.toRadians(45)), Math.toRadians(45))
                // Pickup left ground
                .splineToLinearHeading(new Pose2d(-72 + (17.5 / 2) + 17, -24, Math.toRadians(180)), Math.toRadians(90))
                .splineToLinearHeading(new Pose2d(-56, -56, Math.toRadians(45)), Math.toRadians(45))
                .build());


        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}