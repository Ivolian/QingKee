package com.unicorn.qingkee.activity.base;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.view.ViewHelper;
import com.unicorn.qingkee.R;

import butterknife.InjectView;

public abstract class ScrollableActivity extends ToolbarActivity  implements ObservableScrollViewCallbacks {

    protected boolean mStickyToolbar = true;

    @InjectView(R.id.scroll)
    protected ObservableScrollView observableScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        observableScrollView.setScrollViewCallbacks(this);
    }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
    }

    @Override
    public void onDownMotionEvent() {
    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {

        if (!mStickyToolbar){
            return;
        }

        if (scrollState == ScrollState.UP) {
            if (toolbarIsShown()) {
                hideToolbar();
            }
        } else if (scrollState == ScrollState.DOWN) {
            if (toolbarIsHidden()) {
                showToolbar();
            }
        }
    }

    private boolean toolbarIsShown() {
        return ViewHelper.getTranslationY(mToolbar) == 0;
    }

    private boolean toolbarIsHidden() {
        return ViewHelper.getTranslationY(mToolbar) == -mToolbar.getHeight();
    }

    private void showToolbar() {
        moveToolbar(0);
    }

    private void hideToolbar() {
        moveToolbar(-mToolbar.getHeight());
    }

    private void moveToolbar(float toTranslationY) {
        if (ViewHelper.getTranslationY(mToolbar) == toTranslationY) {
            return;
        }
        ValueAnimator animator = ValueAnimator.ofFloat(ViewHelper.getTranslationY(mToolbar), toTranslationY).setDuration(200);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float translationY = (float) animation.getAnimatedValue();
                ViewHelper.setTranslationY(mToolbar, translationY);
                ViewHelper.setTranslationY(observableScrollView, translationY);
                ViewHelper.setTranslationY(shadow, translationY);

                FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) (observableScrollView).getLayoutParams();
                lp.height = (int) -translationY + getScreenHeight() - lp.topMargin;
                (observableScrollView).requestLayout();
            }
        });
        animator.start();
    }

    protected int getScreenHeight() {
        return findViewById(android.R.id.content).getHeight();
    }

}
