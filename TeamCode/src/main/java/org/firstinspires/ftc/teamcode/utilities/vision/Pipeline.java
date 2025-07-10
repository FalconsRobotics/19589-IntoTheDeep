package org.firstinspires.ftc.teamcode.utilities.vision;

import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;

public class Pipeline {
    final protected int id;
    final protected Limelight3A limelight;

    /** Final suggested action supplied by results from the pipeline loop. */
    protected Pose3D suggestedAction;


    /** Initializes a pipeline with an ID and limelight object. */
    protected Pipeline(int id, HardwareMap map) {
        this.id = id;
        limelight = map.get(Limelight3A.class, "limelight");
    }


    // The following implementations should be continued by child classes.
    public void init() {
        limelight.start();
        limelight.pipelineSwitch(id);

        suggestedAction = new Pose3D(new Position(), new YawPitchRollAngles(AngleUnit.RADIANS, 0, 0, 0, 0));
    }

    /** Returns whether or not pipeline should end. */
    boolean loop() {
        // To be defined entirely by children.
        return true;
    }

    void end() {
        limelight.stop();
    }

    Pose3D getSuggestedAction() {
        return suggestedAction;
    }
}
