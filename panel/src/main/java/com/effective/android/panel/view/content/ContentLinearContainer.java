package com.effective.android.panel.view.content;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.IdRes;
import androidx.annotation.Nullable;

import com.effective.android.panel.R;


/**
 * --------------------
 * | PanelSwitchLayout  |
 * |  ----------------  |
 * | |                | |
 * | |ContentContainer| |
 * | |                | |
 * |  ----------------  |
 * |  ----------------  |
 * | | PanelContainer | |
 * |  ----------------  |
 * --------------------
 * Created by yummyLau on 2020/05/07
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */
public class ContentLinearContainer extends LinearLayout implements IContentContainer {

    @IdRes
    int editTextId;
    @IdRes
    int emptyViewId;

    private ContentContainerImpl contentContainer;

    public ContentLinearContainer(Context context) {
        this(context, null);
    }

    public ContentLinearContainer(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ContentLinearContainer(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(attrs, defStyleAttr, 0);
    }

    @TargetApi(21)
    public ContentLinearContainer(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(attrs, defStyleAttr, defStyleRes);
    }

    private void initView(AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        final TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ContentLinearContainer, defStyleAttr, 0);
        if (typedArray != null) {
            editTextId = typedArray.getResourceId(R.styleable.ContentLinearContainer_linear_edit_view, -1);
            emptyViewId = typedArray.getResourceId(R.styleable.ContentLinearContainer_linear_empty_view, -1);
            typedArray.recycle();
        }
        setOrientation(VERTICAL);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        contentContainer = new ContentContainerImpl(this, editTextId, emptyViewId);
    }

    @Override
    public void layoutGroup(int l, int t, int r, int b) {
        contentContainer.layoutGroup(l, t, r, b);
    }

    public View findTriggerView(int id) {
        return contentContainer.findTriggerView(id);
    }

    @Override
    public void adjustHeight(int targetHeight) {
        contentContainer.adjustHeight(targetHeight);
    }


    @Override
    public void emptyViewVisible(boolean visible) {
        contentContainer.emptyViewVisible(visible);
    }

    @Override
    public void setEmptyViewClickListener(OnClickListener l) {
        contentContainer.setEmptyViewClickListener(l);
    }

    @Override
    public EditText getEditText() {
        return contentContainer.getEditText();
    }

    @Override
    public void setEditTextClickListener(OnClickListener l) {
        contentContainer.setEditTextClickListener(l);
    }

    @Override
    public void setEditTextFocusChangeListener(OnFocusChangeListener l) {
        contentContainer.setEditTextFocusChangeListener(l);
    }

    @Override
    public void clearFocusByEditText() {
        contentContainer.clearFocusByEditText();
    }

    @Override
    public void requestFocusByEditText() {
        contentContainer.requestFocusByEditText();
    }

    @Override
    public boolean editTextHasFocus() {
        return contentContainer.editTextHasFocus();
    }

    @Override
    public void preformClickForEditText() {
        contentContainer.preformClickForEditText();
    }
}
