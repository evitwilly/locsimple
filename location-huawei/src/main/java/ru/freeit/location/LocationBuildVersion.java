package ru.freeit.location;

import android.os.Build;

public class LocationBuildVersion {
    public static boolean isAndroid12() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.S;
    }
}
