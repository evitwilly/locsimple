package ru.freeit.lib;

import android.location.Location;

import androidx.annotation.NonNull;

interface LocationServiceListener {
    void onLocation(@NonNull Location location);
}
