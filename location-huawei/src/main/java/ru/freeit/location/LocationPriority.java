package ru.freeit.location;


public enum LocationPriority {
    HIGH_ACCURACY(com.huawei.hms.location.LocationRequest.PRIORITY_HIGH_ACCURACY),
    BALANCED_POWER_ACCURACY(com.huawei.hms.location.LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY),
    NO_POWER(com.huawei.hms.location.LocationRequest.PRIORITY_NO_POWER),
    LOW_POWER(com.huawei.hms.location.LocationRequest.PRIORITY_LOW_POWER);

    private final int priorityHuawei;

    LocationPriority(final int priorityHuawei) {
        this.priorityHuawei = priorityHuawei;
    }

    public int priorityHuawei() {
        return priorityHuawei;
    }
}
