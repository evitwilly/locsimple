package ru.freeit.location;

import androidx.activity.ComponentActivity;
import androidx.fragment.app.Fragment;

public class LocationSimpleSingle extends LocationAbstractSimple {

    public LocationSimpleSingle(ComponentActivity activity) {
        super(activity);
    }

    public LocationSimpleSingle(Fragment fragment) {
        super(fragment);
    }

    public LocationSimpleSingle(final ComponentActivity activity, final NeededLocationPermission neededLocationPermission) {
        super(activity, neededLocationPermission);
    }

    public LocationSimpleSingle(final Fragment fragment, final NeededLocationPermission neededLocationPermission) {
        super(fragment, neededLocationPermission);
    }

    public LocationSimpleSingle(final ComponentActivity activity, final LocationRequestSettings requestSettings) {
        super(activity, requestSettings);
    }

    public LocationSimpleSingle(final Fragment fragment, final LocationRequestSettings requestSettings) {
        super(fragment, requestSettings);
    }

    public LocationSimpleSingle(
            final ComponentActivity activity,
            final NeededLocationPermission neededLocationPermission,
            final LocationRequestSettings requestSettings
    ) {
        super(activity, neededLocationPermission, requestSettings);
    }

    public LocationSimpleSingle(
            final Fragment fragment,
            final NeededLocationPermission neededLocationPermission,
            final LocationRequestSettings requestSettings
    ) {
        super(fragment, neededLocationPermission, requestSettings);
    }

    @Override
    public void findMyLocation() {
        locationLastKnown.location(lastKnownLocation -> {
            if (lastKnownLocation != null && simpleCallback != null) {
                simpleCallback.onLocation(lastKnownLocation);
            } else {
                service.startService(context, location -> {
                    if (simpleCallback != null) {
                        simpleCallback.onLocation(location);
                        service.stopService();
                    }
                });
            }
        });
    }

}
