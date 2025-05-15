package com.gec.ecommerce.bases;

public class MetadataDto {
    private long totalElements;
    private int totalPages;
    private int perPage;
    private int currentPage;
    private int nextPage;
    private int previousPage;
    private String currentPageUrl;
    private String firstPageUrl;
    private String lastPageUrl;
    private String nextPageUrl;
    private String previousPageUrl;

    public MetadataDto(long totalElements, int totalPages, int perPage, int currentPage, int nextPage, int previousPage,
                       String currentPageUrl, String firstPageUrl, String lastPageUrl, String nextPageUrl, String previousPageUrl) {
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.perPage = perPage;
        this.currentPage = currentPage;
        this.nextPage = nextPage;
        this.previousPage = previousPage;
        this.currentPageUrl = currentPageUrl;
        this.firstPageUrl = firstPageUrl;
        this.lastPageUrl = lastPageUrl;
        this.nextPageUrl = nextPageUrl;
        this.previousPageUrl = previousPageUrl;
    }
    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getPerPage() {
        return perPage;
    }

    public void setPerPage(int perPage) {
        this.perPage = perPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getNextPage() {
        return nextPage;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }

    public int getPreviousPage() {
        return previousPage;
    }

    public void setPreviousPage(int previousPage) {
        this.previousPage = previousPage;
    }

    public String getCurrentPageUrl() {
        return currentPageUrl;
    }

    public void setCurrentPageUrl(String currentPageUrl) {
        this.currentPageUrl = currentPageUrl;
    }

    public String getFirstPageUrl() {
        return firstPageUrl;
    }

    public void setFirstPageUrl(String firstPageUrl) {
        this.firstPageUrl = firstPageUrl;
    }

    public String getLastPageUrl() {
        return lastPageUrl;
    }

    public void setLastPageUrl(String lastPageUrl) {
        this.lastPageUrl = lastPageUrl;
    }

    public String getNextPageUrl() {
        return nextPageUrl;
    }

    public void setNextPageUrl(String nextPageUrl) {
        this.nextPageUrl = nextPageUrl;
    }

    public String getPreviousPageUrl() {
        return previousPageUrl;
    }

    public void setPreviousPageUrl(String previousPageUrl) {
        this.previousPageUrl = previousPageUrl;
    }
}
