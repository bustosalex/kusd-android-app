package edu.uwp.kusd.homepage;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;

public class NoScrollGridLayoutManager extends GridLayoutManager {

    /**
     * Boolean for if scrolling is enabled
     */
    private boolean isScrollEnabled = true;

    /**
     * Contructs a NoScrollGridLayoutManager
     *
     * @param context   the context
     * @param spanCount the number of columns
     */
    public NoScrollGridLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
    }

    /**
     * Sets scrolling to be disabled
     *
     * @param flag the flag for scrolling
     */
    public void setScrollEnabled(boolean flag) {
        this.isScrollEnabled = flag;
    }

    /**
     * Checks if the manger can scroll vertically
     *
     * @return boolean for if the manager can scroll vertically
     */
    @Override
    public boolean canScrollVertically() {
        return isScrollEnabled && super.canScrollVertically();
    }
}

