package com.example.emojikeyboard.keyboard.emoji

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatImageView
import com.example.emojikeyboard.R
import com.example.emojikeyboard.utils.dp2pxInt

/**
 * Created by ss
 * on 2020/5/8
 *
 * desc:
 */
class EmojiTabLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : LinearLayout(context, attrs, defStyleAttr) {

    interface OnEmojiTabLayoutClickListener {
        fun onTabClick(position: Int)
    }

    private var mOnEmojiTabLayoutClickListener: OnEmojiTabLayoutClickListener? = null

    init {
        gravity = Gravity.CENTER
        orientation = HORIZONTAL
        View.inflate(context, R.layout.lay_face_pan_tab, this)
        setState(0)
        setPadding(dp2pxInt(16.0f), 0, dp2pxInt(16.0f), 0)
    }

    fun setOnEmojiTabLayoutClickListener(onEmojiTabLayoutClickListener: OnEmojiTabLayoutClickListener) {
        this.mOnEmojiTabLayoutClickListener = onEmojiTabLayoutClickListener
    }

    fun setState(position: Int) {
        for (index in 0..childCount) {
            val child = getChildAt(index) as? AppCompatImageView ?: continue
            child.tag = index
            child.setOnClickListener {
                val tmpPosition = it.tag as Int
                setState(tmpPosition)
                mOnEmojiTabLayoutClickListener?.onTabClick(tmpPosition)
            }
            child.isActivated = index == position
        }
    }
}