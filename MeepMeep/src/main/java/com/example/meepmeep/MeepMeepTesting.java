package com.example.meepmeep;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.core.colorscheme.ColorScheme;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

import org.jetbrains.annotations.NotNull;

import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Point2D;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                //.addEntity(BuildRedBucketSequence(meepMeep))
                //.addEntity(BuildBlueBucketSequence(meepMeep))
                .addEntity(BuildRedSpecimenSequence(meepMeep))
                .start();
    }

    public static RoadRunnerBotEntity BuildRedBucketSequence(MeepMeep meepMeep){
        RoadRunnerBotEntity redBucketBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        Pose2d redBucketStart = new Pose2d(-34, -59, Math.toRadians(180));
        Pose2d redBucketStripe1 = new Pose2d(-48,-39, Math.toRadians(90));
        Pose2d redBucketStripe2 = new Pose2d(-58,-39, Math.toRadians(90));
        Pose2d redBucketStripe3 = new Pose2d(-55,-25, Math.toRadians(180));
        Pose2d redBucket = new Pose2d(-52, -52, Math.toRadians(225));

        double pickTime = 2.0;
        double dropTime = 4.0;
        redBucketBot.runAction(
                redBucketBot.getDrive().actionBuilder(redBucketStart)
                        .waitSeconds(.5)
                        .splineTo(redBucketStripe1.position, redBucketStripe1.heading)
                        .waitSeconds(pickTime)
                        .strafeTo(new Vector2d(redBucketStripe1.position.x+10,redBucketStripe1.position.y))
                        .turnTo(redBucket.heading)
                        .splineTo(redBucket.position, redBucket.heading)
                        .waitSeconds(dropTime)
                        .splineTo(redBucketStripe2.position, redBucketStripe2.heading)
                        .waitSeconds(pickTime)
                        .splineTo(redBucket.position, redBucket.heading)
                        .waitSeconds(dropTime)
                        .splineTo(redBucketStripe3.position, redBucketStripe3.heading)
                        .waitSeconds(pickTime)
                        .lineToX(redBucketStripe3.position.x+10)
                        .turnTo(redBucket.heading)
                        .splineTo(redBucket.position, redBucket.heading)
                        .build()
        );
        return redBucketBot;
    }

    public static RoadRunnerBotEntity BuildBlueBucketSequence(MeepMeep meepMeep){
        RoadRunnerBotEntity bot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        Pose2d startPose = new Pose2d(34, 59, Math.toRadians(0));
        Pose2d stripe1Pose = new Pose2d(48,39, Math.toRadians(270));
        Pose2d stripe2Pose = new Pose2d(58,39, Math.toRadians(270));
        Pose2d stripe3Pose = new Pose2d(55,25, Math.toRadians(0));
        Pose2d bucketPose = new Pose2d(52, 52, Math.toRadians(45));

        double pickTime = 2.0;
        double dropTime = 4.0;
        bot.runAction(
                bot.getDrive().actionBuilder(startPose)
                        .waitSeconds(.5)
                        .splineTo(stripe1Pose.position, stripe1Pose.heading)
                        .waitSeconds(pickTime)
                        .strafeTo(new Vector2d(stripe1Pose.position.x-10,stripe1Pose.position.y))
                        .turnTo(bucketPose.heading)
                        .splineTo(bucketPose.position, bucketPose.heading)
                        .waitSeconds(dropTime)
                        //.lineToX(bucketPose.position.x-10)
                        .splineTo(stripe2Pose.position, stripe2Pose.heading)
                        .waitSeconds(pickTime)
                        .splineTo(bucketPose.position, bucketPose.heading)
                        .waitSeconds(dropTime)
                        .splineTo(stripe3Pose.position, stripe3Pose.heading)
                        .waitSeconds(pickTime)
                        .lineToX(stripe3Pose.position.x-10)
                        .turnTo(bucketPose.heading)
                        .splineTo(bucketPose.position, bucketPose.heading)
                        .build()
        );
        return bot;
    }

    public static RoadRunnerBotEntity BuildRedSpecimenSequence(MeepMeep meepMeep){
        RoadRunnerBotEntity redBucketBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        Pose2d redBucketStart = new Pose2d(11, -62, Math.toRadians(90));
        Pose2d redHangZone = new Pose2d(0, -32, Math.toRadians(90));
        Pose2d redDropZone = new Pose2d(56, -55, Math.toRadians(270));

        Pose2d redBucketStripe1 = new Pose2d(38,-39, Math.toRadians(90));
        Pose2d redBucketStripe2 = new Pose2d(58,-39, Math.toRadians(90));
        Pose2d redBucketStripe3 = new Pose2d(56,-25, Math.toRadians(0));

        double pickTime = 0.1;
        double hangTime = 0.1;
        double dropTime = 0.1;
        redBucketBot.runAction(
                redBucketBot.getDrive().actionBuilder(redBucketStart)
                        .waitSeconds(.5)
                        .splineTo(redHangZone.position, redHangZone.heading)
                        .waitSeconds(hangTime)
                        .lineToYConstantHeading(redHangZone.position.y-20)
                        .strafeTo(new Vector2d(redBucketStripe1.position.x+10,redBucketStripe1.position.y))
                        .turnTo(redBucketStripe1.heading)
                        .splineTo(redDropZone.position, redDropZone.heading)
                        .waitSeconds(dropTime)
                        //.lineToYConstantHeading(redDropZone.position.y+20)
                        .splineTo(redBucketStripe2.position, redBucketStripe2.heading)
                        .waitSeconds(pickTime)
//                        .splineTo(redBucket.position, redBucket.heading)
//                        .waitSeconds(dropTime)
//                        .splineTo(redBucketStripe3.position, redBucketStripe3.heading)
//                        .waitSeconds(pickTime)
//                        .lineToX(redBucketStripe3.position.x+10)
//                        .turnTo(redBucket.heading)
//                        .splineTo(redBucket.position, redBucket.heading)
                        .build()
        );
        return redBucketBot;
    }
}