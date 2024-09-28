package com.travelsmart.trip_service.constant;

import java.util.List;

public enum TripPermission {
    OWNER,EDIT,READ;
    public static List<TripPermission> getPermssions(){
        return List.of(TripPermission.EDIT,TripPermission.READ);
    }
}
