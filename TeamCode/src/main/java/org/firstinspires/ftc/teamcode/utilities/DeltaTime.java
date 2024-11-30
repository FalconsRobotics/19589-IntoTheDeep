package org.firstinspires.ftc.teamcode.utilities;

public class DeltaTime {
    private double delta;
    private double lastTimestamp;

    public DeltaTime() {
        // preparations for update call.
        lastTimestamp = System.currentTimeMillis();
        delta = 0;
    }

    /** updates delta. Should be called once every iteration of main loop. */
    public void update() {
        double current = System.currentTimeMillis();
        double difference = (current - lastTimestamp) * (1.0 / 1000.0); // Converted to seconds here,

        // If application gets stuck for awhile, simulate a delta of 0.1 to avoid a large
        // multiplications
        delta = Math.min(difference, 0.1);
        lastTimestamp = current;
    }

    /** Returns time (in seconds) between each update() call. */
    public double get() {
        return delta;
    }
}
