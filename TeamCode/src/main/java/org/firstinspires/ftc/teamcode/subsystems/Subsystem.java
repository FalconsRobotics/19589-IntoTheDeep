package org.firstinspires.ftc.teamcode.subsystems;

public interface Subsystem {
    /** Miscellaneous procedures that need to be ran every iteration */
    void update();

    /** Cleans up resources used by subsystem. */
    void finalize();
}
