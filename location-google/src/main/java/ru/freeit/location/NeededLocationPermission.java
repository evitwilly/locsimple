package ru.freeit.location;

import android.Manifest;

public enum NeededLocationPermission {
    EXACTLY(Manifest.permission.ACCESS_COARSE_LOCATION), INACCURATE(Manifest.permission.ACCESS_FINE_LOCATION);

    private final String permission;

    NeededLocationPermission(final String permission) {
        this.permission = permission;
    }

    public String permission() {
        return permission;
    }
}
