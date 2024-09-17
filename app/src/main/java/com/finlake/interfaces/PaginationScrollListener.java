package com.finlake.interfaces;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public abstract class PaginationScrollListener extends RecyclerView.OnScrollListener {

    private final LinearLayoutManager layoutManager;

    public PaginationScrollListener(LinearLayoutManager layoutManager) {
        this.layoutManager = layoutManager;
    }

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        int visibleItemCount = layoutManager.getChildCount();
        int totalItemCount = layoutManager.getItemCount();
        int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
        System.out.println("scrolling1 " + visibleItemCount + " " + totalItemCount + " " + firstVisibleItemPosition);
        if (!isLoading() && !isLastPage()) {
            System.out.println("scrolling2 " + visibleItemCount + " " + totalItemCount + " " + firstVisibleItemPosition);
            if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0) {
                System.out.println("scrolling3 " + visibleItemCount + " " + totalItemCount + " " + firstVisibleItemPosition);
                loadMoreItems();
            }
        }
    }

    protected abstract void loadMoreItems();

    protected abstract int getTotalPageCount();

    protected abstract boolean isLastPage();

    protected abstract boolean isLoading();

}