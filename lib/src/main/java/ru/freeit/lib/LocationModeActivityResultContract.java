package ru.freeit.lib;

import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.provider.Settings;

import androidx.activity.ComponentActivity;
import androidx.activity.result.ActivityResultCaller;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

class LocationModeActivityResultContract {
    private final ActivityResultLauncher<Intent> launcher;

    public LocationModeActivityResultContract(final Context context, final ActivityResultCaller caller, final LocationModeCallback callback) {
        launcher = caller.registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            callback.onReturned();
        });
    }

    public void openLocationSettings() {
        final Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        launcher.launch(intent);
    }
}
