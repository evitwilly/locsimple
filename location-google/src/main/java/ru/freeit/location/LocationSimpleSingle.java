package ru.freeit.location;

import androidx.activity.ComponentActivity;
import androidx.fragment.app.Fragment;

public class LocationSimpleSingle extends LocationAbstractSimple {

    public LocationSimpleSingle(ComponentActivity activity) { super(activity); }
    public LocationSimpleSingle(Fragment fragment) { super(fragment); }

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
