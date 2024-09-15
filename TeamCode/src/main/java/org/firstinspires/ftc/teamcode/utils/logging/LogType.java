package org.firstinspires.ftc.teamcode.utils.logging;

public enum LogType {
    L("left", "ticks"),
    R("right", "ticks"),
    F("front", "ticks"),
    X("x", "in"),
    Y("y", "in"),
    H("h", "in");

    private final String header;
    private final String unit;

    LogType(String header, String unit) {
        this.header = header;
        this.unit = unit;
    }

    public String getHeader() {
        return this.header;
    }

    public String getUnit() {
        return this.unit;
    }
}
