package ru.freeit.location;

import android.content.Context;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApi;

class LocationGoogleAvailability {
    private static final GoogleApiAvailability availability = GoogleApiAvailability.getInstance();
    public static boolean isGoogleAvailable(final Context context) {
        final int status = availability.isGooglePlayServicesAvailable(context);
        return status == ConnectionResult.SUCCESS || status == ConnectionResult.SERVICE_UPDATING
            || status == ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED;
    }
}
