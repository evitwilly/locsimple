package ru.freeit.location;

import com.google.android.gms.location.LocationRequest;

public final class LocationRequestSettings {
    private final LocationPriority priority;
    private final long interval;
    private final long fastestInterval;
    private final int numUpdates;
    private final float smallestDisplacement;
    private final long maxTime;
    private final boolean isWaitForAccurateLocation;

    public LocationRequestSettings(final LocationPriority priority, final long interval, final long fastestInterval, final int numUpdates,
                                   final float smallestDisplacement, final long maxTime, final boolean isWaitForAccurateLocation) {
        this.priority = priority;
        this.interval = interval;
        this.fastestInterval = fastestInterval;
        this.numUpdates = numUpdates;
        this.smallestDisplacement = smallestDisplacement;
        this.maxTime = maxTime;
        this.isWaitForAccurateLocation = isWaitForAccurateLocation;
    }

    public LocationRequestSettings() {
        this(LocationPriority.HIGH_ACCURACY, 5000L, 1000L,
                -1, -1f, -1L, false);
    }

    public LocationRequestSettings changedPriority(final LocationPriority priority) {
        return new LocationRequestSettings(priority, interval, fastestInterval, numUpdates,
                smallestDisplacement, maxTime, isWaitForAccurateLocation);
    }

    public LocationRequestSettings changedInterval(final long interval) {
        return new LocationRequestSettings(priority, interval, fastestInterval, numUpdates,
                smallestDisplacement, maxTime, isWaitForAccurateLocation);
    }

    public LocationRequestSettings changedFastestInterval(final long fastestInterval) {
        return new LocationRequestSettings(priority, interval, fastestInterval, numUpdates,
                smallestDisplacement, maxTime, isWaitForAccurateLocation);
    }

    public LocationRequestSettings changedNumUpdates(final int numUpdates) {
        return new LocationRequestSettings(priority, interval, fastestInterval, numUpdates,
                smallestDisplacement, maxTime, isWaitForAccurateLocation);
    }

    public LocationRequestSettings changedSmallestDisplacement(final float smallestDisplacement) {
        return new LocationRequestSettings(priority, interval, fastestInterval, numUpdates,
                smallestDisplacement, maxTime, isWaitForAccurateLocation);
    }

    public LocationRequestSettings changedMaxTime(final long maxTime) {
        return new LocationRequestSettings(priority, interval, fastestInterval, numUpdates,
                smallestDisplacement, maxTime, isWaitForAccurateLocation);
    }

    public LocationRequestSettings changedWaitForAccurateLocation(final boolean isWaitForAccurateLocation) {
        return new LocationRequestSettings(priority, interval, fastestInterval, numUpdates,
                smallestDisplacement, maxTime, isWaitForAccurateLocation);
    }

    LocationRequest locationRequest() {
        final LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(priority.priority());
        if (interval >= 0) {
            locationRequest.setInterval(interval);
        }
        if (fastestInterval >= 0) {
            locationRequest.setFastestInterval(fastestInterval);
        }
        if (numUpdates > 0) {
            locationRequest.setNumUpdates(numUpdates);
        }
        if (smallestDisplacement >= 0f) {
            locationRequest.setSmallestDisplacement(smallestDisplacement);
        }
        if (maxTime > 0) {
            locationRequest.setMaxWaitTime(maxTime);
        }
        locationRequest.setWaitForAccurateLocation(isWaitForAccurateLocation);
        return locationRequest;
    }
}
