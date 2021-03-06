package ru.freeit.location;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

class LocationService implements DefaultLifecycleObserver {
    private LocationProviderClient locationProviderClient;
    private final LocationRequestSettings requestSettings;

    public LocationService(final LifecycleOwner lifecycleOwner, final LocationRequestSettings requestSettings) {
        lifecycleOwner.getLifecycle().addObserver(this);
        this.requestSettings = requestSettings;
        this.locationProviderClient = new LocationProviderClientHuawei();
    }

    public LocationService(final Context context, final LifecycleOwner lifecycleOwner) {
        this(lifecycleOwner, new LocationRequestSettings());
    }

    public void startService(final Context context, final LocationServiceListener listener, final LocationProviderDisabledCallback disabledCallback) {
        locationProviderClient.initialize(context, listener, disabledCallback);
        locationProviderClient.startLocationUpdates(requestSettings);
    }

    private void startLocationUpdates() {
        locationProviderClient.startLocationUpdates(requestSettings);
    }

    @SuppressLint("MissingPermission")
    public void stopService() {
        locationProviderClient.stopLocationUpdates();
    }

    @Override
    public void onResume(@NonNull LifecycleOwner owner) { startLocationUpdates(); }
    @Override
    public void onPause(@NonNull LifecycleOwner owner) { stopService(); }
}
