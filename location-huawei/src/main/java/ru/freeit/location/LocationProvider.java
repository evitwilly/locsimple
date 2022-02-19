package ru.freeit.location;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;


class LocationProvider {
    private final Context context;

    public LocationProvider(final Context context) {
        this.context = context;
    }

    @SuppressLint("MissingPermission")
    public boolean isEnabled() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            final LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            return locationManager.isLocationEnabled();
        } else {
            final int mode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE, Settings.Secure.LOCATION_MODE_OFF);
            return mode != Settings.Secure.LOCATION_MODE_OFF;
        }
    }
}
