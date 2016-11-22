package edu.uwp.kusd.homepage;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;

/**
 * Created by Dakota on 10/30/2016.
 */

public class NoScrollGridLayoutManager extends GridLayoutManager {
    private boolean isScrollEnabled = true;

    public NoScrollGridLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
    }

    public void setScrollEnabled(boolean flag) {
        this.isScrollEnabled = flag;
    }

    @Override
    public boolean canScrollVertically() {
        return isScrollEnabled && super.canScrollVertically();
    }
}

