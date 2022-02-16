package ru.freeit.lib;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;

import androidx.annotation.Nullable;

import java.util.List;

class LocationLastKnown {
    private final LocationManager locationManager;

    public LocationLastKnown(final Context context) {
        this.locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }

    public @Nullable Location location() {
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

        return bestLocation;
    }
}
