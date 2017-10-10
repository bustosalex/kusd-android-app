package edu.uwp.kusd.homepage;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

/**
 * Created by dakota on 9/6/17.
 */

public class HighlightSwipeListener implements View.OnTouchListener {

    private final GestureDetector gestureDetector;

    public HighlightSwipeListener(Context context){
        gestureDetector = new GestureDetector(context, new SwipeListener());
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    private final class SwipeListener extends GestureDetector.SimpleOnGestureListener {

        private static final int SWIPE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            boolean result = false;
            try {
                float diffY = e2.getY() - e1.getY();
                float diffX = e2.getX() - e1.getX();
                if (Math.abs(diffX) > Math.abs(diffY)) {
                    if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffX > 0) {
                            swipeRight();
                        } else {
                            swipeLeft();
                        }
                        result = true;
                    }
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return result;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            tapped();
            return false;
        }
    }

    public void swipeLeft() {

    }

    public void swipeRight() {

    }

    public void tapped() {
    }
}
