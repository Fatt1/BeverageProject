package com.fat.Contract.Shared;

import java.util.List;
import java.util.stream.Stream;

public class PagedResult <T> {

    private List<T> items;
    private int totalItems;
    private int pageIndex;
    private int pageSize;
    private int totalPages;
    private boolean hasNextPage;
    private boolean hasPreviousPage;

    private PagedResult(List<T> items , int totalItems, int pageIndex, int pageSize) {
        this.items = items;
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.totalItems = totalItems;
        this.totalPages = (int) Math.ceil((double) totalItems / pageSize);
        this.hasNextPage = pageIndex < totalPages;
        this.hasPreviousPage = pageIndex > 1;
    }

    public static <T>PagedResult<T> create(Stream<T> stream,int totalItems, Integer pageIndex, Integer pageSize) {
        int effectivePageIndex = (pageIndex == null || pageIndex < 1) ? 1 : pageIndex;
        int effectivePageSize = (pageSize == null || pageSize < 1) ? 10 : pageSize;
        int skipPage = (effectivePageIndex - 1) * effectivePageSize;
        List<T> items = stream
                .skip(skipPage)
                .limit(effectivePageSize)
                .toList();
        return new PagedResult<>(items, totalItems,effectivePageIndex, effectivePageSize);
    }

    public boolean isHasNextPage() {
        return hasNextPage;
    }

    public boolean isHasPreviousPage() {
        return hasPreviousPage;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
