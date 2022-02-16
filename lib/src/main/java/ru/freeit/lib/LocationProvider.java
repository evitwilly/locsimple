package ru.freeit.lib;

import android.content.Context;
import android.location.LocationManager;

class LocationProvider {
    private final LocationManager locationManager;

    public LocationProvider(final Context context) {
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }

    public boolean isEnabled() {
        final boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        final boolean isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        return isNetworkEnabled || isGpsEnabled;
    }


}
