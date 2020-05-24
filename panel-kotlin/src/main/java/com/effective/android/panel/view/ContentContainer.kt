package com.effective.android.panel.view

import android.annotation.TargetApi
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import androidx.annotation.IdRes
import com.effective.android.panel.R
import com.effective.android.panel.view.content.ContentContainerImpl
import com.effective.android.panel.view.content.IContentContainer

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
 * Created by yummyLau on 18-7-10
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 * update by yummylau on 2020/05/07 请使用 [com.effective.android.panel.view.content.ContentLinearContainer] 后续该类将被遗弃
 */
@Deprecated("use ContentLinearContainer instead of it")
class ContentContainer : LinearLayout, IContentContainer {
    @IdRes
    var editTextId = 0
    @IdRes
    var emptyViewId = 0
    private var contentContainer: ContentContainerImpl? = null

    @JvmOverloads
    constructor(context: Context?, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : super(context, attrs, defStyleAttr) {
        initView(attrs, defStyleAttr, 0)
    }

    @TargetApi(21)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        initView(attrs, defStyleAttr, defStyleRes)
    }

    private fun initView(attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ContentContainer, defStyleAttr, 0)
        if (typedArray != null) {
            editTextId = typedArray.getResourceId(R.styleable.ContentContainer_edit_view, -1)
            emptyViewId = typedArray.getResourceId(R.styleable.ContentContainer_empty_view, -1)
            typedArray.recycle()
        }
        orientation = VERTICAL
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        contentContainer = ContentContainerImpl(this, editTextId, emptyViewId)
    }

    override fun layoutGroup(l: Int, t: Int, r: Int, b: Int) {
        contentContainer!!.layoutGroup(l, t, r, b)
    }

    override fun findTriggerView(id: Int): View? {
        return contentContainer!!.findTriggerView(id)
    }

    override fun adjustHeight(targetHeight: Int) {
        contentContainer!!.adjustHeight(targetHeight)
    }

    override fun emptyViewVisible(visible: Boolean) {
        contentContainer!!.emptyViewVisible(visible)
    }

    override fun setEmptyViewClickListener(l: OnClickListener) {
        contentContainer!!.setEmptyViewClickListener(l)
    }

    override fun getEditText(): EditText {
        return contentContainer!!.getEditText()
    }

    override fun setEditTextClickListener(l: OnClickListener) {
        contentContainer!!.setEditTextClickListener(l)
    }

    override fun setEditTextFocusChangeListener(l: OnFocusChangeListener) {
        contentContainer!!.setEditTextFocusChangeListener(l)
    }

    override fun clearFocusByEditText() {
        contentContainer!!.clearFocusByEditText()
    }

    override fun requestFocusByEditText() {
        contentContainer!!.requestFocusByEditText()
    }

    override fun editTextHasFocus(): Boolean {
        return contentContainer!!.editTextHasFocus()
    }

    override fun preformClickForEditText() {
        contentContainer!!.preformClickForEditText()
    }
}