//package com.travelsmart.location_service.mapper;
//
//import com.travelsmart.location_service.entity.LocationEntity;
//import com.travelsmart.location_service.utils.ConvertUtils;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//import java.util.Map;
//
//@Component
//public class LocationHttpMapper {
//    public LocationEntity mappingLocation(ResponseEntity<?> response){
//        Map<?,?> result = (Map<?, ?>) response.getBody();
//        List<Map> arr = (List<Map>) result.get("features");
//        System.out.println(arr);
//        return  arr.stream()
//                .map(data -> ConvertUtils.convert((Map<String, Object>) data.get("properties"),LocationEntity.class))
//                .toList().get(0);
//    }
//
//}
