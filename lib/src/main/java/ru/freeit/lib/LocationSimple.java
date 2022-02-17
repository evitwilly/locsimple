package ru.freeit.lib;

import android.location.Location;

import androidx.activity.ComponentActivity;
import androidx.fragment.app.Fragment;

public class LocationSimple extends LocationAbstractSimple {

    public LocationSimple(ComponentActivity activity) { super(activity); }
    public LocationSimple(Fragment fragment) { super(fragment); }

    @Override
    public void findMyLocation() {
        final Location myLocation = locationLastKnown.location();
        if (myLocation != null && simpleCallback != null) {
            simpleCallback.onLocation(myLocation);
        } else {
            service.startService(context, location -> {
                if (simpleCallback != null) {
                    simpleCallback.onLocation(location);
                    service.stopService();
                }
            });
        }
    }

}
