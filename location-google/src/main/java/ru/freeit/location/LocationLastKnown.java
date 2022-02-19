package ru.freeit.location;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.util.List;

class LocationLastKnown {
    private final LocationManager locationManager;
    private final FusedLocationProviderClient fusedLocationProviderClient;

    public LocationLastKnown(final Context context) {
        if (LocationBuildVersion.isAndroid12()) {
            this.fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);
            this.locationManager = null;
        } else {
            this.fusedLocationProviderClient = null;
            this.locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        }
    }

    @SuppressLint("MissingPermission")
    public void location(final LocationLastKnownCallback callback) {
        if (LocationBuildVersion.isAndroid12()) {
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(location -> {
                callback.onLocation(location);
            })
            .addOnFailureListener(throwable -> {
                callback.onLocation(null);
            });
        } else {
            final List<String> providers = locationManager.getProviders(true);
            Location bestLocation = null;

            for (String provider : providers) {
                @SuppressLint("MissingPermission") final Location location = locationManager.getLastKnownLocation(provider);
                if (location != null) {
                    if (bestLocation == null || (location.getAccuracy() > bestLocation.getAccuracy())) {
                        bestLocation = location;
                    }
                }
            }

            callback.onLocation(bestLocation);
        }
    }
}
