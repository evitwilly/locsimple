package ru.freeit.location;

import com.google.android.gms.location.LocationRequest;

public enum LocationPriority {
    HIGH_ACCURACY(LocationRequest.PRIORITY_HIGH_ACCURACY),
    BALANCED_POWER_ACCURACY(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY),
    NO_POWER(LocationRequest.PRIORITY_NO_POWER),
    LOW_POWER(LocationRequest.PRIORITY_LOW_POWER);

    private final int priority;

    LocationPriority(final int priority) {
        this.priority = priority;
    }

    public int priority() {
        return priority;
    }
}
