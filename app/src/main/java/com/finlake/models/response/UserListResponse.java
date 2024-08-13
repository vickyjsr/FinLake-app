package com.finlake.models.response;

import com.finlake.models.FinanceRoomResponse;
import com.finlake.models.UserResponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class UserListResponse {
    @SerializedName("requestId")
    private String requestId;

    @SerializedName("responseCode")
    private String responseCode;

    @SerializedName("data")
    private Data data;

    public UserListResponse(String requestId, String responseCode, Data data) {
        this.requestId = requestId;
        this.responseCode = responseCode;
        this.data = data;
    }

    public static class Data {
        @SerializedName("content")
        private List<UserResponse> content;

        @SerializedName("pageable")
        private Pageable pageable;

        @SerializedName("last")
        private boolean last;

        @SerializedName("totalPages")
        private int totalPages;

        @SerializedName("totalElements")
        private int totalElements;

        @SerializedName("first")
        private boolean first;

        @SerializedName("size")
        private int size;

        @SerializedName("number")
        private int number;

        @SerializedName("sort")
        private Sort sort;

        @SerializedName("numberOfElements")
        private int numberOfElements;

        @SerializedName("empty")
        private boolean empty;

        public Data(List<UserResponse> content, Pageable pageable, boolean last, int totalPages, int totalElements, boolean first, int size, int number, Sort sort, int numberOfElements, boolean empty) {
            this.content = content;
            this.pageable = pageable;
            this.last = last;
            this.totalPages = totalPages;
            this.totalElements = totalElements;
            this.first = first;
            this.size = size;
            this.number = number;
            this.sort = sort;
            this.numberOfElements = numberOfElements;
            this.empty = empty;
        }

        public List<UserResponse> getContent() {
            return content;
        }

        public void setContent(List<UserResponse> content) {
            this.content = content;
        }

        public Pageable getPageable() {
            return pageable;
        }

        public void setPageable(Pageable pageable) {
            this.pageable = pageable;
        }

        public boolean isLast() {
            return last;
        }

        public void setLast(boolean last) {
            this.last = last;
        }

        public int getTotalPages() {
            return totalPages;
        }

        public void setTotalPages(int totalPages) {
            this.totalPages = totalPages;
        }

        public int getTotalElements() {
            return totalElements;
        }

        public void setTotalElements(int totalElements) {
            this.totalElements = totalElements;
        }

        public boolean isFirst() {
            return first;
        }

        public void setFirst(boolean first) {
            this.first = first;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public Sort getSort() {
            return sort;
        }

        public void setSort(Sort sort) {
            this.sort = sort;
        }

        public int getNumberOfElements() {
            return numberOfElements;
        }

        public void setNumberOfElements(int numberOfElements) {
            this.numberOfElements = numberOfElements;
        }

        public boolean isEmpty() {
            return empty;
        }

        public void setEmpty(boolean empty) {
            this.empty = empty;
        }
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
