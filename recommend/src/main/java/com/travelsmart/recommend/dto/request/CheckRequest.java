package com.travelsmart.recommend.dto.request;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckRequest {
    private List<Long> place_id;
}
