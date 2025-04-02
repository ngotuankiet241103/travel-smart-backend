package com.travelsmart.location_service.dto.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.travelsmart.location_service.constant.LocationType;
import com.travelsmart.location_service.entity.Address;
import com.travelsmart.location_service.utils.StringListConverter;
import io.fabric8.zjsonpatch.internal.guava.Strings;
import jakarta.persistence.Convert;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class LocationUpdateRequest {
    @NotEmpty(message = "CATEGORY_INVALID")
    private String category;
    @NotEmpty(message = "DISPLAY_NAME_INVALID")
    private String display_name;
    @NotEmpty(message = "LON_INVALID")
    private String lon;
    @NotEmpty(message = "LAT_INVALID")
    private String lat;
    @NotEmpty(message = "NAME_INVALID")
    private String name;
    @NotNull(message = "TYPE_INVALID")
    private LocationType type;
    @NotNull(message = "ADDRESS_INVALID")
    private Address address;
    @NotNull(message = "IMAGE_INVALID")
    private Long imageId;
    @NotNull(message = "BOUNDINGBOX_INVALID")
    private List<String> boundingbox;
    public String getDisplayName() {
        ObjectMapper objectMapper = new ObjectMapper();
        StringBuilder builder = new StringBuilder(this.name);
        Map<String,Object> map = objectMapper.convertValue(this.getAddress(),Map.class);
        map.keySet().stream().forEach(key -> {
            if(!key.equals("postcode") && !key.equals("country_code") && !key.equals("country")){
                if( !Strings.isNullOrEmpty(map.get(key)+"") && map.get(key) != null){
                    builder.append(", ");
                    builder.append(map.get(key));
                }
            }

        });
        System.out.println(builder.toString());
        return builder.toString();
    }
}
