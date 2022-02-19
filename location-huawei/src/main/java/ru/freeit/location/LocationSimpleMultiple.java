package ru.freeit.location;

import androidx.activity.ComponentActivity;
import androidx.fragment.app.Fragment;

public class LocationSimpleMultiple extends LocationAbstractSimple {

    public LocationSimpleMultiple(ComponentActivity activity) {
        super(activity);
    }

    public LocationSimpleMultiple(Fragment fragment) {
        super(fragment);
    }

    public LocationSimpleMultiple(final ComponentActivity activity, final NeededLocationPermission neededLocationPermission) {
        super(activity, neededLocationPermission);
    }

    public LocationSimpleMultiple(final Fragment fragment, final NeededLocationPermission neededLocationPermission) {
        super(fragment, neededLocationPermission);
    }

    public LocationSimpleMultiple(final ComponentActivity activity, final LocationRequestSettings requestSettings) {
        super(activity, requestSettings);
    }

    public LocationSimpleMultiple(final Fragment fragment, final LocationRequestSettings requestSettings) {
        super(fragment, requestSettings);
    }

    public LocationSimpleMultiple(
            final ComponentActivity activity,
            final NeededLocationPermission neededLocationPermission,
            final LocationRequestSettings requestSettings
    ) {
        super(activity, neededLocationPermission, requestSettings);
    }

    public LocationSimpleMultiple(
            final Fragment fragment,
            final NeededLocationPermission neededLocationPermission,
            final LocationRequestSettings requestSettings
    ) {
        super(fragment, neededLocationPermission, requestSettings);
    }

    @Override
    public void findMyLocation() {
        service.startService(context, location -> {
            if (simpleCallback != null) {
                simpleCallback.onLocation(location);
            }
        });
        locationLastKnown.location(lastKnownLocation -> {
            if (simpleCallback != null && lastKnownLocation != null) {
                simpleCallback.onLocation(lastKnownLocation);
            }
        });
    }
}
