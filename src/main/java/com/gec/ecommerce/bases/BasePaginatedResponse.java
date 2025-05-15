package com.gec.ecommerce.bases;

import org.springframework.data.domain.Page;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

public class BasePaginatedResponse<D> {

    private List<D> data;
    private MetadataDto meta;

    public BasePaginatedResponse(Page<D> pageData, UriComponentsBuilder uri) {
        this.data = pageData.getContent();
        this.meta = completeData(pageData, uri);
    }

    private MetadataDto completeData(Page<D> pageData, UriComponentsBuilder uri) {

        long totalElements = pageData.getTotalElements();
        int totalPages = pageData.getTotalPages();
        int perPage = pageData.getPageable().getPageSize();
        int currentPage = pageData.getPageable().getPageNumber();
        int nextPage = pageData.hasNext() ? pageData.nextPageable().getPageNumber() : totalPages;
        int previousPage = pageData.hasPrevious() ? pageData.previousPageable().getPageNumber() : 0;
        String currentPageUrl = uri.cloneBuilder().replaceQueryParam("page")
                .queryParam("page", currentPage).build().toString();
        String firstPageUrl = uri.cloneBuilder().replaceQueryParam("page")
                .queryParam("page", 0).build().toString();
        String lastPageUrl = uri.cloneBuilder().replaceQueryParam("page")
                .queryParam("page", (pageData.getTotalPages() > 0) ? pageData.getTotalPages() - 1 : 0).build().toString();
        String nextPageUrl = pageData.hasNext() ?
                uri.cloneBuilder().replaceQueryParam("page")
                        .queryParam("page", nextPage).build().toString() : null;
        String previousPageUrl = pageData.hasPrevious() ?
                uri.cloneBuilder().replaceQueryParam("page")
                        .queryParam("page", pageData.previousPageable().getPageNumber()).build().toString() : null;

        return new MetadataDto(
                totalElements,
                totalPages,
                perPage,
                currentPage,
                nextPage,
                previousPage,
                currentPageUrl,
                firstPageUrl,
                lastPageUrl,
                nextPageUrl,
                previousPageUrl
        );
    }

    public List<D> getData() {
        return data;
    }

    public void setData(List<D> data) {
        this.data = data;
    }

    public MetadataDto getMeta() {
        return meta;
    }

    public void setMeta(MetadataDto meta) {
        this.meta = meta;
    }
}