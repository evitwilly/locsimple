package ru.freeit.location;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

class LocationService implements DefaultLifecycleObserver {
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationCallback locationCallback;

    private final LocationRequestSettings requestSettings;

    public LocationService(final LifecycleOwner lifecycleOwner, final LocationRequestSettings requestSettings) {
        lifecycleOwner.getLifecycle().addObserver(this);
        this.requestSettings = requestSettings;
    }

    public LocationService(final LifecycleOwner lifecycleOwner) {
        this(lifecycleOwner, new LocationRequestSettings());
    }

    public void startService(final Context context, final LocationServiceListener listener) {
        if (locationCallback == null || fusedLocationProviderClient == null) {
            locationCallback = new LocationCallback() {
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
                    // TODO(don't forget to add Location Availability handling)
                }
            };
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);
            startLocationUpdates();
        }
    }

    @SuppressLint("MissingPermission")
    public void stopService() {
        if (locationCallback != null && fusedLocationProviderClient != null) {
            fusedLocationProviderClient.removeLocationUpdates(locationCallback);
            locationCallback = null;
        }
    }

    @SuppressLint("MissingPermission")
    private void startLocationUpdates() {
        if (fusedLocationProviderClient != null && locationCallback != null) {
            final LocationRequest locationRequest = requestSettings.locationRequest();
            fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
        }
    }

    @Override
    public void onResume(@NonNull LifecycleOwner owner) { startLocationUpdates(); }
    @Override
    public void onPause(@NonNull LifecycleOwner owner) { stopService(); }
}
