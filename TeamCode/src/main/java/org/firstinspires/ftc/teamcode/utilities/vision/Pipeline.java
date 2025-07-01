package org.firstinspires.ftc.teamcode.utilities.vision;

import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Pipeline {
    final protected int id;
    final protected Limelight3A limelight;


    /** Initializes a pipeline with an ID and limelight object. */
    protected Pipeline(int id, HardwareMap map) {
        this.id = id;
        limelight = map.get(Limelight3A.class, "limelight");
    }


    // The following implementations should be continued by child classes.
    public void init() {
        limelight.start();
        limelight.pipelineSwitch(id);
    }

    /** Returns whether or not pipeline should end. */
    boolean loop() {
        // To be defined entirely by children.
        return true;
    }

    void end() {
        limelight.stop();
    }
}
