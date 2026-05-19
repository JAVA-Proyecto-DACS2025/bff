package com.dacs.bff.graphql;

import java.util.List;
import com.dacs.bff.dto.PersonalDto;

public class PersonalConnection {
    private List<PersonalDto.BackResponse> content;
    private long totalElements;
    private int totalPages;
    private int currentPage;
    private boolean hasNextPage;
    private boolean hasPreviousPage;

    public List<PersonalDto.BackResponse> getContent() { return content; }
    public void setContent(List<PersonalDto.BackResponse> content) { this.content = content; }
    public long getTotalElements() { return totalElements; }
    public void setTotalElements(long totalElements) { this.totalElements = totalElements; }
    public int getTotalPages() { return totalPages; }
    public void setTotalPages(int totalPages) { this.totalPages = totalPages; }
    public int getCurrentPage() { return currentPage; }
    public void setCurrentPage(int currentPage) { this.currentPage = currentPage; }
    public boolean isHasNextPage() { return hasNextPage; }
    public void setHasNextPage(boolean hasNextPage) { this.hasNextPage = hasNextPage; }
    public boolean isHasPreviousPage() { return hasPreviousPage; }
    public void setHasPreviousPage(boolean hasPreviousPage) { this.hasPreviousPage = hasPreviousPage; }
}
