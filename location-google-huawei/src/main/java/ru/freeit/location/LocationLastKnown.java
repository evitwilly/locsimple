package ru.freeit.location;

import android.annotation.SuppressLint;
import android.content.Context;


class LocationLastKnown {
    private final com.google.android.gms.location.FusedLocationProviderClient fusedLocationProviderClientGoogle;
    private final com.huawei.hms.location.FusedLocationProviderClient fusedLocationProviderClientHuawei;

    public LocationLastKnown(final Context context) {
        if (LocationGoogleAvailability.isGoogleAvailable(context)) {
            this.fusedLocationProviderClientGoogle = com.google.android.gms.location.LocationServices.getFusedLocationProviderClient(context);
            this.fusedLocationProviderClientHuawei = null;
        } else {
            this.fusedLocationProviderClientGoogle = null;
            this.fusedLocationProviderClientHuawei = com.huawei.hms.location.LocationServices.getFusedLocationProviderClient(context);
        }
    }

    @SuppressLint("MissingPermission")
    public void location(final LocationLastKnownCallback callback) {
        if (fusedLocationProviderClientGoogle != null) {
            fusedLocationProviderClientGoogle.getLastLocation()
                    .addOnSuccessListener(location -> callback.onLocation(location))
                    .addOnFailureListener(throwable -> callback.onLocation(null));
        } else {
            fusedLocationProviderClientHuawei.getLastLocation()
                    .addOnSuccessListener(location -> callback.onLocation(location))
                    .addOnFailureListener(throwable -> callback.onLocation(null));
        }
    }
}
