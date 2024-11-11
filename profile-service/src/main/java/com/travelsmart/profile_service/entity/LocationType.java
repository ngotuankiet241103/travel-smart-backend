package com.travelsmart.profile_service.entity;

import com.travelsmart.profile_service.dto.response.LocationTypeResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
@AllArgsConstructor
public enum LocationType {
    TOURIST_ATTRACTION(120,"https://th.bing.com/th/id/OIP.x4Y73HqteD5wq03_ob2vEAHaE7?rs=1&pid=ImgDetMain"),   // 2 hours
    RESTAURANT(90,"https://th.bing.com/th/id/OIP.amQn-069ILTY9lIhukkvQQHaE8?rs=1&pid=ImgDetMain"),            // 1.5 hours
    SHOPPING(180,"https://th.bing.com/th/id/R.ae8adc50c0cac9835d87feabc029802d?rik=mfrZw7MMvxFJJQ&pid=ImgRaw&r=0"),             // 3 hours
    PARK(120,"https://th.bing.com/th/id/R.b4987eb205f97e9e49a30287ee6cb96d?rik=ZQ03i%2b7mVbRXCQ&pid=ImgRaw&r=0"),                 // 2 hours
    CULTURAL_SITE(60,"https://th.bing.com/th/id/OIP.Sw_p4wL1XiRKexy281Qc4gHaFj?rs=1&pid=ImgDetMain"),         // 1 hour
    ACCOMMODATION(0,"https://th.bing.com/th/id/OIP.DQH2XqDmoL2ukliF3Jxb4gHaEK?rs=1&pid=ImgDetMain"),          // No set time (for stays)
    ENTERTAINMENT(150,"https://th.bing.com/th/id/OIP.JnFztPhJLNKZSa1NOqxHGgHaE7?rs=1&pid=ImgDetMain"),        // 2.5 hours
    TRANSPORT_HUB(30,"https://th.bing.com/th/id/OIP.8C9xe6NHuByHjMfKPTbJ9QHaE7?rs=1&pid=ImgDetMain"),         // 30 minutes for passing through
    EVENT(180,"https://th.bing.com/th/id/OIP.hb36FiH88QndGKXlZBq-vgHaFj?rs=1&pid=ImgDetMain"),                // 3 hours (can vary)
    ADVENTURE(240,"https://th.bing.com/th/id/OIP.FoGMP8cbxV62cJ-Qhes29AHaE8?rs=1&pid=ImgDetMain"),            // 4 hours (can vary based on activity)
    HEALTH_WELLNESS(120,"https://th.bing.com/th/id/OIP.js_m--7-FZoWCNrtRA-kQwHaFj?rs=1&pid=ImgDetMain"),      // 2 hours for typical sessions
    EDUCATIONAL(90,"https://th.bing.com/th/id/OIP.Ps7tYTjU4dIJTdrXQViD0QHaE8?rs=1&pid=ImgDetMain"),           // 1.5 hours
    FUNCTIONAL(15,"https://th.bing.com/th/id/OIP.zCU3iB-MNGSoFHCjHOpflwHaE8?rs=1&pid=ImgDetMain"),    // 15 minutes for quick visits like gas stations or ATMs
    ADMINISTRATIVE(30,"https://static-images.vnncdn.net/files/publish/2022/11/21/ho-guom-1613.jpg");
    private final int timeToVisitMinutes;
    private final  String image;


    // Constructor
    public static List<LocationType> getLocationTypes(){
        return Arrays.asList(LocationType.values());
    }

    public static List<LocationTypeResponse> getHobbies() {
        return getLocationTypes().stream()
                .map(locationType -> LocationTypeResponse.builder()
                        .label(locationType.toString())
                        .image(locationType.getImage())
                        .build()
                ).toList();
    }
}
