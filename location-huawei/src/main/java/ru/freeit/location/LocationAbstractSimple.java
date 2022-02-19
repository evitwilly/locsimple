package ru.freeit.location;

import android.content.Context;
import android.content.pm.PackageManager;

import androidx.activity.ComponentActivity;
import androidx.activity.result.ActivityResultCaller;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;

abstract class LocationAbstractSimple {
    protected final Context context;
    protected final LocationLastKnown locationLastKnown;
    protected final LocationService service;
    protected LocationSimpleCallback simpleCallback;

    private final LocationPermission locationPermission;
    private final LocationProvider provider;
    private final LocationModeActivityResultContract locationModeActivityResultContract;

    private LocationPermissionDeniedCallback deniedCallback;
    private LocationShutdownCallback shutdownCallback;

    private LocationAbstractSimple(
            final Context context,
            final LifecycleOwner owner,
            final ActivityResultCaller caller,
            final NeededLocationPermission neededLocationPermission,
            final LocationRequestSettings locationRequestSettings
    ) {
        this.context = context;
        this.provider = new LocationProvider(context);
        this.locationLastKnown = new LocationLastKnown(context);
        this.service = new LocationService(context, owner, locationRequestSettings);

        this.locationModeActivityResultContract = new LocationModeActivityResultContract(caller, () -> {
            if (provider.isEnabled()) findMyLocation();
            else if (shutdownCallback != null) shutdownCallback.onShutdown();
        });

        locationPermission = new LocationPermission(context, neededLocationPermission, caller, () -> {
            if (provider.isEnabled()) findMyLocation();
            else locationModeActivityResultContract.openLocationSettings();
        }, () -> {
            if (deniedCallback != null) deniedCallback.onPermissionDenied();
        });

    }

    public LocationAbstractSimple(final ComponentActivity activity, final NeededLocationPermission neededLocationPermission, final LocationRequestSettings requestSettings) {
        this(activity, activity, activity, neededLocationPermission, requestSettings);
    }

    public LocationAbstractSimple(final ComponentActivity activity, final NeededLocationPermission neededLocationPermission) {
        this(activity, activity, activity, neededLocationPermission, new LocationRequestSettings());
    }

    public LocationAbstractSimple(final ComponentActivity activity, final LocationRequestSettings requestSettings) {
        this(activity, activity, activity, NeededLocationPermission.INACCURATE, requestSettings);
    }

    public LocationAbstractSimple(final ComponentActivity activity) {
        this(activity, NeededLocationPermission.INACCURATE, new LocationRequestSettings());
    }

    public LocationAbstractSimple(final Fragment fragment, final NeededLocationPermission neededLocationPermission, final LocationRequestSettings requestSettings) {
        this(fragment.requireContext(), fragment, fragment, neededLocationPermission, requestSettings);
    }

    public LocationAbstractSimple(final Fragment fragment, final NeededLocationPermission neededLocationPermission) {
        this(fragment.requireContext(), fragment, fragment, neededLocationPermission, new LocationRequestSettings());
    }

    public LocationAbstractSimple(final Fragment fragment, final LocationRequestSettings requestSettings) {
        this(fragment.requireContext(), fragment, fragment, NeededLocationPermission.INACCURATE, requestSettings);
    }

    public LocationAbstractSimple(final Fragment fragment) {
        this(fragment, NeededLocationPermission.INACCURATE, new LocationRequestSettings());
    }

    public void onPermissionDenied(final LocationPermissionDeniedCallback deniedCallback) {
        this.deniedCallback = deniedCallback;
    }

    public void onLocationShutdown(final LocationShutdownCallback shutdownCallback) {
        this.shutdownCallback = shutdownCallback;
    }

    public void defineLocation(final LocationSimpleCallback callback) {
        this.simpleCallback = callback;
        locationPermission.requestPermission();
    }

    abstract void findMyLocation();
}
