package io.straas.android.sdk.circall.demo.ui;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class FlashHint extends android.support.v7.widget.AppCompatImageView {

    static final long FLASH_INTERVAL = 1000;

    public boolean mFlashEnabled;

    Runnable mRunFlash = new Runnable() {
        @Override
        public void run() {
            removeCallbacks(this);
            if (!mFlashEnabled) {
                return;
            }
            FlashHint.this.setVisibility(
                    FlashHint.this.getVisibility() == VISIBLE
                            ? INVISIBLE
                            : VISIBLE);
            postDelayed(this, FLASH_INTERVAL);
        }
    };

    public FlashHint(Context context) {
        this(context, null);
    }

    public FlashHint(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlashHint(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setVisibility(View.GONE);
    }

    public void setFlash(boolean enabled) {
        mFlashEnabled = enabled;
        if (mFlashEnabled) {
            bringToFront();
            removeCallbacks(mRunFlash);
            postDelayed(mRunFlash, FLASH_INTERVAL);
        } else {
            setVisibility(View.INVISIBLE);
            removeCallbacks(mRunFlash);
        }
    }

    @BindingAdapter("app:flash")
    public static void setFlash(FlashHint flashHint, boolean enabled) {
        flashHint.setFlash(enabled);
    }
}
