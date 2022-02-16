package ru.freeit.lib;

import androidx.activity.ComponentActivity;
import androidx.fragment.app.Fragment;

class LocationAbstractSimpleMultiple extends LocationAbstractSimple {

    public LocationAbstractSimpleMultiple(ComponentActivity activity) { super(activity); }
    public LocationAbstractSimpleMultiple(Fragment fragment) { super(fragment); }

    @Override
    public void findMyLocation() {
        locationLastKnown.location();
        service.startService(context, location -> {
            if (callback != null) {
                callback.onLocation(location);
            }
        });
    }
}
