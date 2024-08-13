package com.finlake.models.response;

import com.google.gson.annotations.SerializedName;

public class Sort {
    @SerializedName("empty")
    private boolean empty;

    @SerializedName("sorted")
    private boolean sorted;

    @SerializedName("unsorted")
    private boolean unsorted;

    public Sort(boolean empty, boolean sorted, boolean unsorted) {
        this.empty = empty;
        this.sorted = sorted;
        this.unsorted = unsorted;
    }

    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }

    public boolean isSorted() {
        return sorted;
    }

    public void setSorted(boolean sorted) {
        this.sorted = sorted;
    }

    public boolean isUnsorted() {
        return unsorted;
    }

    public void setUnsorted(boolean unsorted) {
        this.unsorted = unsorted;
    }
}