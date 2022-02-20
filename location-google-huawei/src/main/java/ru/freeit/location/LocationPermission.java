package ru.freeit.location;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.activity.result.ActivityResultCaller;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.ContextCompat;

class LocationPermission {
    private final Context context;
    private final ActivityResultLauncher<String[]> launcher;
    private final Runnable isAllowed;
    private final NeededLocationPermission neededLocationPermission;

    public LocationPermission(
            final Context context,
            final NeededLocationPermission neededLocationPermission,
            final ActivityResultCaller caller,
            final Runnable isAllowed,
            final Runnable isDenied) {

        this.context = context;
        this.isAllowed = isAllowed;
        this.neededLocationPermission = neededLocationPermission;
        launcher = caller.registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), result -> {
            for (boolean granted : result.values()) {
                if (granted) {
                    isAllowed.run();
                    return;
                }
            }
            isDenied.run();
        });
    }

    private final String accessFineLocationPermission = Manifest.permission.ACCESS_FINE_LOCATION;
    private final String accessCoarseLocation = Manifest.permission.ACCESS_COARSE_LOCATION;

    private final int granted = PackageManager.PERMISSION_GRANTED;

    public boolean isGranted() {
        final int accessFineLocationPermissionGranted = ContextCompat.checkSelfPermission(context, accessFineLocationPermission);
        final int accessCoarseLocationPermissionGranted = ContextCompat.checkSelfPermission(context, accessCoarseLocation);
        return (accessFineLocationPermissionGranted == granted || accessCoarseLocationPermissionGranted == granted);
    }

    public void requestPermission() {
        final boolean isGranted = isGranted();
        if (isGranted) {
            isAllowed.run();
        } else {
            if (LocationBuildVersion.isAndroid12()) {
                launcher.launch(new String[]{ accessFineLocationPermission, accessCoarseLocation });
            } else {
                launcher.launch(new String[]{ neededLocationPermission.permission() });
            }
        }
    }


}
