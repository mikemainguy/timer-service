package com.example.timer;

/**
 * Created by michaelmainguy on 2/20/16.
 */
public enum Status {
    SCHEDULED,
    CANCELLED,
    TRIGGERED,
    COMPLETED,
    UNKNOWN;

    public static Status fromString(String input) {
        switch (input) {
            case "SCHEDULED":
                return Status.SCHEDULED;
            case "CANCELLED":
                return Status.CANCELLED;
            case "TRIGGERED":
                return Status.TRIGGERED;
            case "COMPLETED":
                return Status.TRIGGERED;


        }
        return Status.UNKNOWN;

    }
}
