package com.travelsmart.recommend.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckRequest {
    @NotNull(message = "PLACE")
    private List<Long> place_id;
}
