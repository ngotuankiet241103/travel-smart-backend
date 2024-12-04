package com.travelsmart.profile_service.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public enum HobbyType {
    CULTURAL_TOURISM("Du lịch văn hóa",List.of(LocationType.CULTURAL_SITE, LocationType.TOURIST_ATTRACTION,LocationType.SHOPPING),"https://th.bing.com/th/id/OIP.r0tkqFmJ8Pj6kOJU9QiRZwHaFD?rs=1&pid=ImgDetMain"),
    RELAXATION_TOURISM("Du lịch khám phá",List.of(LocationType.TOURIST_ATTRACTION, LocationType.CULTURAL_SITE, LocationType.ADVENTURE,LocationType.SHOPPING,LocationType.ENTERTAINMENT),"https://th.bing.com/th/id/OIP.dqcrmC5oL3_fKr4f53KwuQHaEO?rs=1&pid=ImgDetMain"),
    EXPLORATION_TOURISM("Du lịch nghỉ dưỡng",List.of(LocationType.ACCOMMODATION, LocationType.PARK, LocationType.HEALTH_WELLNESS,LocationType.TRANSPORT_HUB),"https://th.bing.com/th/id/R.609e871f148a370134aa85023508545c?rik=J4AP6IYUqG5wOw&pid=ImgRaw&r=0"),
    ECO_TOURISM("Du lịch sinh thái",List.of(LocationType.PARK,LocationType.CULTURAL_SITE,LocationType.TOURIST_ATTRACTION,LocationType.TRANSPORT_HUB),"https://media-cdn-v2.laodong.vn/Storage/NewsPortal/2022/12/23/1130434/Du-Lich-Sinh-Thai-Co-01.jpg"),
    ADVENTURE_TOURISM("Du lịch mạo hiểm",List.of(LocationType.ADVENTURE,LocationType.HEALTH_WELLNESS,LocationType.CULTURAL_SITE,LocationType.TOURIST_ATTRACTION),"https://netintravel.com/wp-content/uploads/2021/08/26-scaled.jpg"),
    CULINARY_TOURISM("Du lịch ẩm thực",List.of(LocationType.RESTAURANT,LocationType.SHOPPING,LocationType.TOURIST_ATTRACTION),"https://th.bing.com/th/id/OIP.TU-_xTKObXpS0Mq1PGjmBwHaE8?rs=1&pid=ImgDetMain"),
    SPIRITUAL_TOURISM("Du lịch tâm linh",List.of(LocationType.CULTURAL_SITE,LocationType.HEALTH_WELLNESS,LocationType.TRANSPORT_HUB),"https://th.bing.com/th/id/R.66ee1a525269c55664adcaf94fc9da3b?rik=E1k4eWOn7uaKcA&riu=http%3a%2f%2feragenx.com%2fwp-content%2fuploads%2f2016%2f12%2ftaj-mahal.jpg&ehk=nw7hwsh%2bkzIwHOa%2bQfsZZn8mxMCCrtkEDoIlF5MZngI%3d&risl=&pid=ImgRaw&r=0"),
    ISLAND_TOURISM("Du lịch biển đảo",List.of(LocationType.TOURIST_ATTRACTION, LocationType.HEALTH_WELLNESS,LocationType.TRANSPORT_HUB),"https://th.bing.com/th/id/R.681b6b9ae14ba782fc12d4c494bd3d62?rik=sa1yRjbJGQ3JDw&pid=ImgRaw&r=0"),
    COMMUNITY_TOURISM("Du lịch cộng đồng",List.of(LocationType.FUNCTIONAL,LocationType.ADMINISTRATIVE,LocationType.ENTERTAINMENT,LocationType.CULTURAL_SITE,LocationType.TOURIST_ATTRACTION,LocationType.TRANSPORT_HUB),"https://th.bing.com/th/id/R.caad79191c699805db6b710b110a0878?rik=ne1EyjqoZH5csw&pid=ImgRaw&r=0"),
    FESTIVAL_TOURISM("Du lịch lễ hội",List.of(LocationType.EVENT,LocationType.SHOPPING,LocationType.ENTERTAINMENT,LocationType.CULTURAL_SITE,LocationType.TOURIST_ATTRACTION),"https://th.bing.com/th/id/R.b82e80fd3abec7fbbeb900207fbe1948?rik=PrIDmgGcKaFWvQ&riu=http%3a%2f%2f4.bp.blogspot.com%2f-9XD_vesi7iM%2fUvNUkXneWmI%2fAAAAAAAAA6I%2f62xSXGncFh0%2fs1600%2fhoi-lim.jpg&ehk=ra1UAla0R08JzFONQh1Xp8QODtCDedeWpv4WeJxnbiU%3d&risl=&pid=ImgRaw&r=0"),
    SPORT_TOURISM("Du lịch thể thao",List.of(LocationType.HEALTH_WELLNESS,LocationType.CULTURAL_SITE,LocationType.TOURIST_ATTRACTION),"https://th.bing.com/th/id/OIP.i_5EEiotPZFMZ6SaGzmbmgHaFI?rs=1&pid=ImgDetMain"),
    HONEYMOON_TOURISM("Du lịch tuần trăng mật",List.of(LocationType.ACCOMMODATION,LocationType.ENTERTAINMENT,LocationType.SHOPPING,LocationType.HEALTH_WELLNESS),"https://th.bing.com/th/id/OIP.Jtd1ooHPoBhWuQD8k0y8tQHaEY?rs=1&pid=ImgDetMain");


    private final String name;
    private final List<LocationType> types;
    private final String image;
    // const travelCategories = {
//   CULTURAL_TOURISM: {
//     vietnameseName: "Du lịch văn hóa",
//     types: ["CULTURAL_SITE", "TOURIST_ATTRACTION","SHOPPING"],
//   },
//   RELAXATION_TOURISM: {
//     vietnameseName: "Du lịch nghỉ dưỡng",
//     types: ["ACCOMMODATION", "PARK", "HEALTH_WELLNESS","TRANSPORT_HUB"],
//   },
//   EXPLORATION_TOURISM: {
//     vietnameseName: "Du lịch khám phá",
//     types: ["TOURIST_ATTRACTION", "CULTURAL_SITE", "ADVENTURE","ADMINISTRATIVE","SHOPPING","ENTERTAINMENT"],
//   },
//   ECO_TOURISM: {
//     vietnameseName: "Du lịch sinh thái",
//     types: ["PARK","ADMINISTRATIVE","CULTURAL_SITE","TOURIST_ATTRACTION","TRANSPORT_HUB"],
//   },
//   ADVENTURE_TOURISM: {
//     vietnameseName: "Du lịch mạo hiểm",
//     types: ["ADVENTURE","HEALTH_WELLNESS","CULTURAL_SITE","TOURIST_ATTRACTION"],
//   },
//   CULINARY_TOURISM: {
//     vietnameseName: "Du lịch ẩm thực",
//     types: ["RESTAURANT","SHOPPING","TOURIST_ATTRACTION"],
//   },
//   SPIRITUAL_TOURISM: {
//     vietnameseName: "Du lịch tâm linh",
//     types: ["CULTURAL_SITE","HEALTH_WELLNESS","TRANSPORT_HUB"],
//   },
//   EDUCATIONAL_TOURISM: {
//     vietnameseName: "Du lịch giáo dục",
//     types: ["EDUCATIONAL","ADMINISTRATIVE","ENTERTAINMENT","TRANSPORT_HUB"],
//   },
//   ISLAND_TOURISM: {
//     vietnameseName: "Du lịch biển đảo",
//     types: ["TOURIST_ATTRACTION", "HEALTH_WELLNESS","TRANSPORT_HUB"],
//   },
//   COMMUNITY_TOURISM: {
//     vietnameseName: "Du lịch cộng đồng",
//     types: ["FUNCTIONAL","ADMINISTRATIVE","ENTERTAINMENT","CULTURAL_SITE","TOURIST_ATTRACTION","TRANSPORT_HUB"],
//   },
//   FESTIVAL_TOURISM: {
//     vietnameseName: "Du lịch mùa lễ hội",
//     types: ["EVENT","SHOPPING","ENTERTAINMENT","CULTURAL_SITE","TOURIST_ATTRACTION"],
//   },
//   SPORT_TOURISM: {
//     vietnameseName: "Du lịch thể thao",
//     types: ["HEALTH_WELLNESS","CULTURAL_SITE","TOURIST_ATTRACTION"],
//   },
//   HONEYMOON_TOURISM: {
//     vietnameseName: "Du lịch tuần trăng mật",
//     types: ["ACCOMMODATION","ENTERTAINMENT","SHOPPING","HEALTH_WELLNESS"],
//   },
// };
}
