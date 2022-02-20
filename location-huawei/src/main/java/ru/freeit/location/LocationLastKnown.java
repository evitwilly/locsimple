package ru.freeit.location;

import android.annotation.SuppressLint;
import android.content.Context;


class LocationLastKnown {
    private final com.huawei.hms.location.FusedLocationProviderClient fusedLocationProviderClientHuawei;

    public LocationLastKnown(final Context context) {
        this.fusedLocationProviderClientHuawei = com.huawei.hms.location.LocationServices.getFusedLocationProviderClient(context);
    }

    @SuppressLint("MissingPermission")
    public void location(final LocationLastKnownCallback callback) {
        fusedLocationProviderClientHuawei.getLastLocation()
                .addOnSuccessListener(location -> callback.onLocation(location))
                .addOnFailureListener(throwable -> callback.onLocation(null));
    }
}
