package ru.freeit.location;

import android.content.Intent;
import android.provider.Settings;

import androidx.activity.result.ActivityResultCaller;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

class LocationModeActivityResultContract {
    private final ActivityResultLauncher<Intent> launcher;

    public LocationModeActivityResultContract(final ActivityResultCaller caller, final LocationModeCallback callback) {
        launcher = caller.registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> callback.onReturned());
    }

    public void openLocationSettings() {
        final Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        launcher.launch(intent);
    }
}
