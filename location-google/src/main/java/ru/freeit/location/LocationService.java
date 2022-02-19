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
    private final LocationProviderClient locationProviderClient;
    private final LocationRequestSettings requestSettings;

    public LocationService(final LifecycleOwner lifecycleOwner, final LocationRequestSettings requestSettings) {
        lifecycleOwner.getLifecycle().addObserver(this);
        this.requestSettings = requestSettings;
        this.locationProviderClient = new LocationProviderClientGoogle();
    }

    public LocationService(final LifecycleOwner lifecycleOwner) {
        this(lifecycleOwner, new LocationRequestSettings());
    }

    public void startService(final Context context, final LocationServiceListener listener, final LocationProviderDisabledCallback disabledCallback) {
        locationProviderClient.initialize(context, listener, disabledCallback);
        locationProviderClient.startLocationUpdates(requestSettings);
    }

    @SuppressLint("MissingPermission")
    public void stopService() { locationProviderClient.stopLocationUpdates(); }
    @SuppressLint("MissingPermission")
    private void startLocationUpdates() { locationProviderClient.startLocationUpdates(requestSettings); }

    @Override
    public void onResume(@NonNull LifecycleOwner owner) { startLocationUpdates(); }
    @Override
    public void onPause(@NonNull LifecycleOwner owner) { stopService(); }
}
