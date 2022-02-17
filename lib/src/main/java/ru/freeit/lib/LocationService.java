package ru.freeit.lib;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.location.LocationListenerCompat;
import androidx.core.location.LocationManagerCompat;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

import java.util.List;


class LocationService implements DefaultLifecycleObserver {
    private LocationManager locationManager;
    private LocationListener locationListener;

    private final float minDistanceMeters = 1f;
    private final long intervalMillis = 1000L;



    public LocationService(final LifecycleOwner lifecycleOwner) {
        lifecycleOwner.getLifecycle().addObserver(this);
    }

    public void startService(final Context context, final LocationServiceListener listener) {
        if (locationListener == null || locationManager == null) {
            locationListener = new LocationListenerCompat() {

                @Override
                public void onLocationChanged(@NonNull Location location) {
                    listener.onLocation(location);
                }

                @Override
                public void onProviderEnabled(@NonNull String provider) {
                    final int num = 1 + 2;
                }

                @Override
                public void onProviderDisabled(@NonNull String provider) {
                    final int num = 1 + 2;
                }
            };
            locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            startLocationUpdates();
        }
    }

    @SuppressLint("MissingPermission")
    public void stopService() {
        if (locationListener != null && locationManager != null) {
            locationManager.removeUpdates(locationListener);
            locationListener = null;
        }
    }

    @SuppressLint("MissingPermission")
    private void startLocationUpdates() {
        if (locationListener != null && locationManager != null) {
            boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            boolean isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            if (isNetworkEnabled) {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, intervalMillis, minDistanceMeters, locationListener);
            } else if (isGpsEnabled) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, intervalMillis, minDistanceMeters, locationListener);
            }
        }
    }

    @Override
    public void onResume(@NonNull LifecycleOwner owner) { startLocationUpdates(); }
    @Override
    public void onPause(@NonNull LifecycleOwner owner) { stopService(); }
}
