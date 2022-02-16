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

    protected LocationSimpleCallback callback;

    private LocationAbstractSimple(final Context context, final LifecycleOwner owner, final ActivityResultCaller caller) {
        this.context = context;
        provider = new LocationProvider(context);
        locationLastKnown = new LocationLastKnown(context);
        service = new LocationService(owner);

        locationModeActivityResultContract = new LocationModeActivityResultContract(context, caller, () -> {
            if (provider.isEnabled()) findMyLocation();
        });

        permission = new LocationPermission(context, caller, () -> {
            if (provider.isEnabled()) findMyLocation();
            else locationModeActivityResultContract.openLocationSettings();
        }, () -> {});

    }

    public LocationAbstractSimple(final ComponentActivity activity) {
        this(activity, activity, activity);
    }

    public LocationAbstractSimple(final Fragment fragment) {
        this(fragment.requireContext(), fragment, fragment);
    }

    public void defineLocation(final LocationSimpleCallback callback) {
        this.callback = callback;
        permission.requestPermission();
    }

    abstract void findMyLocation();
}
