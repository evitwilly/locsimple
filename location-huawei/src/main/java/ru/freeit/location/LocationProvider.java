package ru.freeit.location;

import android.annotation.SuppressLint;
import android.content.Context;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

class LocationProvider {
    private final FusedLocationProviderClient fusedLocationClient;

    public LocationProvider(final Context context) {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
    }

    @SuppressLint("MissingPermission")
    public void checkEnabled(final LocationProviderCallback callback) {
        fusedLocationClient.getLocationAvailability()
                .addOnSuccessListener(result -> callback.onEnabled())
                .addOnFailureListener(error -> callback.onDisabled());
    }


}
