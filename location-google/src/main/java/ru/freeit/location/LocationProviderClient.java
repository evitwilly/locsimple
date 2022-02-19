package ru.freeit.location;

import android.content.Context;

interface LocationProviderClient {
    void initialize(final Context context, final LocationServiceListener listener, final LocationProviderDisabledCallback callback);
    void startLocationUpdates(final LocationRequestSettings locationRequestSettings);
    void stopLocationUpdates();
}
