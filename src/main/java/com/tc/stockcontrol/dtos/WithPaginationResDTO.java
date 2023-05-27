package com.tc.stockcontrol.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record WithPaginationResDTO<T>(
        T items,
        Integer currentPage,
        Integer totalPages,
        Long totalRecords
) {
}
