package ru.freeit.location;

import android.annotation.SuppressLint;
import android.content.Context;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

class LocationLastKnown {
    private final FusedLocationProviderClient fusedLocationProviderClient;

    public LocationLastKnown(final Context context) {
        this.fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);
    }

    @SuppressLint("MissingPermission")
    public void location(final LocationLastKnownCallback callback) {
        fusedLocationProviderClient.getLastLocation()
                .addOnSuccessListener(location -> { callback.onLocation(location); })
                .addOnFailureListener(throwable -> { callback.onLocation(null); });
    }
}
