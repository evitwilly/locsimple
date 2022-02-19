package ru.freeit.location;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.os.Looper;

import androidx.annotation.NonNull;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

class LocationProviderClientGoogle implements LocationProviderClient {
    private FusedLocationProviderClient fusedLocationProviderClientGoogle;
    private LocationCallback locationCallbackGoogle;

    @Override
    public void initialize(final Context context, final LocationServiceListener listener, final LocationProviderDisabledCallback callback) {
        if (fusedLocationProviderClientGoogle == null) {
            fusedLocationProviderClientGoogle = LocationServices.getFusedLocationProviderClient(context);
        }
        if (locationCallbackGoogle == null) {
            final LocationProvider provider = new LocationProvider(context);
            locationCallbackGoogle = new LocationCallback() {

                @Override
                public void onLocationResult(@NonNull LocationResult locationResult) {
                    super.onLocationResult(locationResult);
                    final Location location = locationResult.getLastLocation();
                    if (location != null) {
                        listener.onLocation(location);
                    }
                }

                @Override
                public void onLocationAvailability(@NonNull LocationAvailability locationAvailability) {
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
            final LocationRequest locationRequest = locationRequestSettings.locationRequest();
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
