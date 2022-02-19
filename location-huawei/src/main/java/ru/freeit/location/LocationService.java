package ru.freeit.location;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApi;

interface LocationProviderClient {
    void initialize(final Context context, final LocationServiceListener listener);
    void startLocationUpdates(final LocationRequestSettings locationRequestSettings);
    void stopLocationUpdates();
}

class LocationProviderClientGoogle implements LocationProviderClient {
    private com.google.android.gms.location.FusedLocationProviderClient fusedLocationProviderClientGoogle;
    private com.google.android.gms.location.LocationCallback locationCallbackGoogle;

    @Override
    public void initialize(final Context context, final LocationServiceListener listener) {
        if (fusedLocationProviderClientGoogle == null) {
            fusedLocationProviderClientGoogle = com.google.android.gms.location.LocationServices.getFusedLocationProviderClient(context);
        }
        if (locationCallbackGoogle == null) {
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
                    // TODO(don't forget to add Location Availability handling)
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

class LocationProviderClientHuawei implements LocationProviderClient {
    private com.huawei.hms.location.FusedLocationProviderClient fusedLocationProviderClientHuawei;
    private com.huawei.hms.location.LocationCallback locationCallbackHuawei;

    @Override
    public void initialize(final Context context, final LocationServiceListener listener) {
        if (fusedLocationProviderClientHuawei == null) {
            fusedLocationProviderClientHuawei = com.huawei.hms.location.LocationServices.getFusedLocationProviderClient(context);
        }
        if (locationCallbackHuawei == null) {
            locationCallbackHuawei = new com.huawei.hms.location.LocationCallback() {

                @Override
                public void onLocationResult(@NonNull com.huawei.hms.location.LocationResult locationResult) {
                    super.onLocationResult(locationResult);
                    final Location location = locationResult.getLastLocation();
                    if (location != null) {
                        listener.onLocation(location);
                    }
                }

                @Override
                public void onLocationAvailability(@NonNull com.huawei.hms.location.LocationAvailability locationAvailability) {
                    super.onLocationAvailability(locationAvailability);
                    // TODO(don't forget to add Location Availability handling)
                }
            };
        }
    }

    @SuppressLint("MissingPermission")
    @Override
    public void startLocationUpdates(final LocationRequestSettings locationRequestSettings) {
        if (fusedLocationProviderClientHuawei != null && locationCallbackHuawei != null) {
            final com.huawei.hms.location.LocationRequest locationRequest = locationRequestSettings.locationRequestHuawei();
            fusedLocationProviderClientHuawei.requestLocationUpdates(locationRequest, locationCallbackHuawei, Looper.getMainLooper());
        }
    }

    @Override
    public void stopLocationUpdates() {
        if (fusedLocationProviderClientHuawei != null && locationCallbackHuawei != null) {
            fusedLocationProviderClientHuawei.removeLocationUpdates(locationCallbackHuawei);
            locationCallbackHuawei = null;
        }
    }

}

class LocationService implements DefaultLifecycleObserver {
    private LocationProviderClient locationProviderClient;
    private final LocationRequestSettings requestSettings;

    public LocationService(final Context context, final LifecycleOwner lifecycleOwner, final LocationRequestSettings requestSettings) {
        lifecycleOwner.getLifecycle().addObserver(this);
        this.requestSettings = requestSettings;
        if (LocationGoogleAvailability.isGoogleAvailable(context)) {
            this.locationProviderClient = new LocationProviderClientGoogle();
        } else {
            this.locationProviderClient = new LocationProviderClientHuawei();
        }
    }

    public LocationService(final Context context, final LifecycleOwner lifecycleOwner) {
        this(context, lifecycleOwner, new LocationRequestSettings());
    }

    public void startService(final Context context, final LocationServiceListener listener) {
        locationProviderClient.initialize(context, listener);
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
