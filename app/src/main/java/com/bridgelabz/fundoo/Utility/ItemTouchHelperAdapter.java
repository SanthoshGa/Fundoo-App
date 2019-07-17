package com.bridgelabz.fundoo.Utility;

public interface ItemTouchHelperAdapter {
    void onItemMove(int fromPosition, int toPosition);

    void onItemSwiped(int position);
}