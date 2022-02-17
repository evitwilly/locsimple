package ru.freeit.lib;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.activity.ComponentActivity;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultCaller;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.util.Map;

class LocationPermission {
    private final Context context;
    private final ActivityResultLauncher<String[]> launcher;
    private final Runnable isAllowed;

    public LocationPermission(
            final Context context,
            final ActivityResultCaller caller, final Runnable isAllowed,
            final Runnable isDenied) {
        this.context = context;
        this.isAllowed = isAllowed;
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
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                launcher.launch(new String[]{ Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION });
            } else {
                launcher.launch(new String[]{ Manifest.permission.ACCESS_FINE_LOCATION });
            }
        }
    }


}
