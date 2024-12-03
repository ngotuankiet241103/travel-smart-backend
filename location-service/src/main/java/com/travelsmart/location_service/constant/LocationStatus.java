package com.travelsmart.location_service.constant;

import java.util.ArrayList;
import java.util.List;

public enum LocationStatus {
    ACCEPT,PENDING,REJECT;

    public static List<LocationStatus> getStatuses() {
        return List.of(ACCEPT,PENDING,REJECT);
    }
}
