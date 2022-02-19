package ru.freeit.location;

import android.location.Location;

import androidx.annotation.Nullable;

interface LocationLastKnownCallback {
    void onLocation(@Nullable Location location);
}
