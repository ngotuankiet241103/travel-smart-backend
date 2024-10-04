package com.travelsmart.location_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Paging {
    private int page;
    private int limit;
    private int totalPages;
    public static Paging buildPaging(Pageable pageable, int totalPages){
        return  Paging.builder()
                .page(pageable.getPageNumber() + 1)
                .limit(pageable.getPageSize())
                .totalPages(totalPages)
                .build();
    }

}
