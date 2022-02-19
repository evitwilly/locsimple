package ru.freeit.location;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.core.location.LocationListenerCompat;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.SettingsClient;

class LocationService implements DefaultLifecycleObserver {
    private LocationManager locationManager;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private SettingsClient settingsClient;

    private LocationCallback locationCallback;
    private LocationListener locationListener;

    private final float minDistanceMeters = 1f;
    private final long intervalMillis = 1000L;

    public LocationService(final LifecycleOwner lifecycleOwner) {
        lifecycleOwner.getLifecycle().addObserver(this);
    }

    public void startService(final Context context, final LocationServiceListener listener) {
        if (LocationBuildVersion.isAndroid12()) {
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
                settingsClient = LocationServices.getSettingsClient(context);
                fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);
            }
        } else {
            if (locationListener == null || locationManager == null) {
                locationListener = new LocationListenerCompat() {

                    @Override
                    public void onLocationChanged(@NonNull Location location) {
                        if (location != null) {
                            listener.onLocation(location);
                        }
                    }

                    @Override
                    public void onProviderEnabled(@NonNull String provider) {

                    }

                    @Override
                    public void onProviderDisabled(@NonNull String provider) {

                    }
                };
                locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            }
        }
        startLocationUpdates();
    }

    @SuppressLint("MissingPermission")
    public void stopService() {
        if (LocationBuildVersion.isAndroid12() && locationCallback != null && fusedLocationProviderClient != null) {
            fusedLocationProviderClient.removeLocationUpdates(locationCallback);
            locationCallback = null;
        } else if (locationListener != null && locationManager != null) {
            locationManager.removeUpdates(locationListener);
            locationListener = null;
        }
    }

    @SuppressLint("MissingPermission")
    private void startLocationUpdates() {
        if (LocationBuildVersion.isAndroid12() && locationCallback != null && fusedLocationProviderClient != null) {
            final LocationRequest locationRequest = LocationRequest.create();
            locationRequest.setInterval(5000L);
            locationRequest.setFastestInterval(1000L);
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
        } else if (locationListener != null && locationManager != null) {
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
