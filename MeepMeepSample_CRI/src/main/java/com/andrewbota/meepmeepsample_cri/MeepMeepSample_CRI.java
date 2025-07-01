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

//        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(24 - (double)(13 / 2), -72 + (17.5 / 2), 0))
        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(35.5, -14.25, 270))
                // Bucket preload
                .splineToLinearHeading(new Pose2d(42, -60.25, Math.toRadians(0)), Math.toRadians(270))
                .build());


        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}