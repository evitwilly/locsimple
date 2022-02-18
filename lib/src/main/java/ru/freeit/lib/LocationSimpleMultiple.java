package ru.freeit.lib;

import androidx.activity.ComponentActivity;
import androidx.fragment.app.Fragment;

public class LocationSimpleMultiple extends LocationAbstractSimple {

    public LocationSimpleMultiple(ComponentActivity activity) { super(activity); }
    public LocationSimpleMultiple(Fragment fragment) { super(fragment); }

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
