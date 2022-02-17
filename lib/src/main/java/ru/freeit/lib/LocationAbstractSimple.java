package ru.freeit.lib;

import android.content.Context;

import androidx.activity.ComponentActivity;
import androidx.activity.result.ActivityResultCaller;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;

abstract class LocationAbstractSimple {
    protected final Context context;
    private final LocationPermission permission;
    private final LocationProvider provider;
    protected final LocationService service;
    protected final LocationLastKnown locationLastKnown;
    private final LocationModeActivityResultContract locationModeActivityResultContract;

    private LocationPermissionDeniedCallback deniedCallback;
    private LocationShutdownCallback shutdownCallback;
    protected LocationSimpleCallback simpleCallback;

    private LocationAbstractSimple(final Context context, final LifecycleOwner owner, final ActivityResultCaller caller) {
        this.context = context;
        provider = new LocationProvider(context);
        locationLastKnown = new LocationLastKnown(context);
        service = new LocationService(owner);

        locationModeActivityResultContract = new LocationModeActivityResultContract(context, caller, () -> {
            if (provider.isEnabled()) {
                findMyLocation();
            } else {
                if (shutdownCallback != null) {
                    shutdownCallback.onShutdown();
                }
            }
        });

        permission = new LocationPermission(context, caller, () -> {
            if (provider.isEnabled()) findMyLocation();
            else locationModeActivityResultContract.openLocationSettings();
        }, () -> {
            if (deniedCallback != null) {
                deniedCallback.onPermissionDenied();
            }
        });

    }

    public LocationAbstractSimple(final ComponentActivity activity) {
        this(activity, activity, activity);
    }

    public LocationAbstractSimple(final Fragment fragment) {
        this(fragment.requireContext(), fragment, fragment);
    }

    public void onPermissionDenied(final LocationPermissionDeniedCallback deniedCallback) {
        this.deniedCallback = deniedCallback;
    }

    public void onLocationShutdown(final LocationShutdownCallback shutdownCallback) {
        this.shutdownCallback = shutdownCallback;
    }

    public void defineLocation(final LocationSimpleCallback callback) {
        this.simpleCallback = callback;
        permission.requestPermission();
    }

    abstract void findMyLocation();
}
