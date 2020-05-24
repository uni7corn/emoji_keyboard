package com.purple.square.biz.msg.chat.widget.emoji

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.emojikeyboard.keyboard.emoji.Emoji
import com.example.emojikeyboard.keyboard.emoji.EmojiAdapter

/**
 * Created by ss
 * on 2020/5/8
 *
 * desc:
 */
class EmojiRecyclerPageList @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : RecyclerView(context, attrs, defStyleAttr) {


    private val mEmojiAdapter = EmojiAdapter()

    init {
        layoutManager = GridLayoutManager(context, 8)
        itemAnimator = null
        adapter = mEmojiAdapter
    }

    fun setState(emojiList: List<Emoji>, onEmojiClickListener: EmojiAdapter.OnEmojiClickListener?) {
        mEmojiAdapter.addAllItems(emojiList)
        mEmojiAdapter.setOnEmojiClickListener(onEmojiClickListener)
    }

}