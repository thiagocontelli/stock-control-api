package com.tc.stockcontrol.dtos;

import org.springframework.stereotype.Component;

@Component
public class WithPaginationResMapper {
    public <T> WithPaginationResDTO<T> toDTO(T t, Integer currentPage, Integer totalPages, Long totalRecords) {
        return new WithPaginationResDTO<>(t, currentPage, totalPages, totalRecords);
    }
}
