package com.purple.square.biz.msg.chat.widget.emoji

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.viewpager2.widget.ViewPager2
import com.example.emojikeyboard.R
import com.example.emojikeyboard.keyboard.emoji.Emoji
import com.example.emojikeyboard.keyboard.emoji.EmojiAdapter
import com.example.emojikeyboard.keyboard.emoji.EmojiPagerAdapter
import com.example.emojikeyboard.keyboard.emoji.EmojiTabLayout
import kotlinx.android.synthetic.main.lay_emoji_view.view.*

/**
 *  Created by sai
 *  on 2020/5/17
 *
 *  desc:
 */
class EmojiPanelView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : LinearLayout(context, attrs, defStyleAttr),
    EmojiTabLayout.OnEmojiTabLayoutClickListener, EmojiAdapter.OnEmojiClickListener {

    private val mEmojiPagerAdapter = EmojiPagerAdapter()
    private var mOnEmojiClickListener: EmojiAdapter.OnEmojiClickListener? = null

    init {
        orientation = VERTICAL
        View.inflate(context, R.layout.lay_emoji_view, this)
        emoji_tab_layout.setOnEmojiTabLayoutClickListener(this)
        emoji_view_pager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        emoji_view_pager.adapter = mEmojiPagerAdapter
        emoji_view_pager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                emoji_tab_layout.setState(position)
            }
        })
        iv_delete.setOnClickListener {
            mOnEmojiClickListener?.onEmojiDelete(emoji = Emoji("-1"), position = -1)
        }
    }

    fun setOnEmojiClickListener(onEmojiClickListener: EmojiAdapter.OnEmojiClickListener) {
        mOnEmojiClickListener = onEmojiClickListener
        mEmojiPagerAdapter.setOnEmojiClickListener(this)
    }

    override fun onTabClick(position: Int) {
        emoji_view_pager.setCurrentItem(position, false)
    }

    override fun onEmojiClick(emoji: Emoji, position: Int) {
        mOnEmojiClickListener?.onEmojiClick(emoji, position)
    }

}