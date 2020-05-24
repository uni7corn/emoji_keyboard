package com.example.emojikeyboard.keyboard.emoji;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class CusRecyclerView extends RecyclerView {

    public boolean startScroll = false;
    private OnResetPanel onResetPanel;

    public CusRecyclerView(Context context) {
        super(context);
    }

    public CusRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CusRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // setLayoutManager(new LinearLayoutManager(context));
    }

    public void setOnResetPanel(OnResetPanel onResetPanel) {
        this.onResetPanel = onResetPanel;
        setItemAnimator(null);
    }

    public interface OnResetPanel {
        void resetPanel();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent e) {
        boolean result = super.onTouchEvent(e);
        if (e.getAction() == MotionEvent.ACTION_DOWN && result) {
            startScroll = false;
        }
        if (e.getAction() == MotionEvent.ACTION_SCROLL && result) {
            startScroll = true;
        }
        if (e.getAction() == MotionEvent.ACTION_UP && result) {
            if (onResetPanel != null && !startScroll) {
                onResetPanel.resetPanel();
            }
        }
        return result;
    }
}
