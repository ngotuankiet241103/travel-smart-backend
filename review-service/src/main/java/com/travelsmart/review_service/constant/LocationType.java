package com.travelsmart.review_service.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
@AllArgsConstructor
public enum LocationType {
    TOURIST_ATTRACTION(120),   // 2 hours
    RESTAURANT(90),            // 1.5 hours
    SHOPPING(180),             // 3 hours
    PARK(120),                 // 2 hours
    CULTURAL_SITE(60),         // 1 hour
    ACCOMMODATION(0),          // No set time (for stays)
    ENTERTAINMENT(150),        // 2.5 hours
    TRANSPORT_HUB(30),         // 30 minutes for passing through
    EVENT(180),                // 3 hours (can vary)
    ADVENTURE(240),            // 4 hours (can vary based on activity)
    HEALTH_WELLNESS(120),      // 2 hours for typical sessions
    EDUCATIONAL(90),           // 1.5 hours
    FUNCTIONAL(15),    // 15 minutes for quick visits like gas stations or ATMs
    ADMINISTRATIVE(30);
    private final int timeToVisitMinutes;

    // Constructor
    public static List<LocationType> getLocationTypes(){
        return Arrays.asList(LocationType.values());
    }

}
