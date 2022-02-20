package ru.freeit.location;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.os.Looper;

import androidx.annotation.NonNull;

class LocationProviderClientGoogle implements LocationProviderClient {
    private com.google.android.gms.location.FusedLocationProviderClient fusedLocationProviderClientGoogle;
    private com.google.android.gms.location.LocationCallback locationCallbackGoogle;

    @Override
    public void initialize(final Context context, final LocationServiceListener listener, final LocationProviderDisabledCallback callback) {
        if (fusedLocationProviderClientGoogle == null) {
            fusedLocationProviderClientGoogle = com.google.android.gms.location.LocationServices.getFusedLocationProviderClient(context);
        }
        if (locationCallbackGoogle == null) {
            final LocationProvider provider = new LocationProvider(context);
            locationCallbackGoogle = new com.google.android.gms.location.LocationCallback() {

                @Override
                public void onLocationResult(@NonNull com.google.android.gms.location.LocationResult locationResult) {
                    super.onLocationResult(locationResult);
                    final Location location = locationResult.getLastLocation();
                    if (location != null) {
                        listener.onLocation(location);
                    }
                }

                @Override
                public void onLocationAvailability(@NonNull com.google.android.gms.location.LocationAvailability locationAvailability) {
                    super.onLocationAvailability(locationAvailability);
                    if (!provider.isEnabled() && callback != null) {
                        callback.onDisabled();
                        stopLocationUpdates();
                    }
                }
            };
        }
    }

    @SuppressLint("MissingPermission")
    @Override
    public void startLocationUpdates(final LocationRequestSettings locationRequestSettings) {
        if (fusedLocationProviderClientGoogle != null && locationCallbackGoogle != null) {
            final com.google.android.gms.location.LocationRequest locationRequest = locationRequestSettings.locationRequestGoogle();
            fusedLocationProviderClientGoogle.requestLocationUpdates(locationRequest, locationCallbackGoogle, Looper.getMainLooper());
        }
    }

    @Override
    public void stopLocationUpdates() {
        if (fusedLocationProviderClientGoogle != null && locationCallbackGoogle != null) {
            fusedLocationProviderClientGoogle.removeLocationUpdates(locationCallbackGoogle);
            locationCallbackGoogle = null;
        }
    }
}
