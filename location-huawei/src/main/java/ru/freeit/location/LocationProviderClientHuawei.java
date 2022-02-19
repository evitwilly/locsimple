package ru.freeit.location;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.os.Looper;

import androidx.annotation.NonNull;

class LocationProviderClientHuawei implements LocationProviderClient {
    private com.huawei.hms.location.FusedLocationProviderClient fusedLocationProviderClientHuawei;
    private com.huawei.hms.location.LocationCallback locationCallbackHuawei;

    @Override
    public void initialize(final Context context, final LocationServiceListener listener, final LocationProviderDisabledCallback callback) {
        if (fusedLocationProviderClientHuawei == null) {
            fusedLocationProviderClientHuawei = com.huawei.hms.location.LocationServices.getFusedLocationProviderClient(context);
        }
        if (locationCallbackHuawei == null) {
            final LocationProvider provider = new LocationProvider(context);
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
