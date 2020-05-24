package com.example.emojikeyboard.keyboard.emoji

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.emojikeyboard.R
import com.example.emojikeyboard.utils.runOnMainThread
import com.example.emojikeyboard.utils.runOnWorkThread
import com.purple.square.biz.msg.chat.widget.emoji.EmojiRecyclerPageList

/**
 *  Created by sai
 *  on 2020/5/17
 *
 *  desc:
 */
class EmojiPagerAdapter : RecyclerView.Adapter<EmojiPagerAdapter.VH>() {

    class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {

        companion object {
            private val TAG = VH::class.java.simpleName
        }

        private val mEmojiList = mutableListOf<Emoji>()
        private val emojiRecyclerPageList = itemView.findViewById<EmojiRecyclerPageList>(R.id.emoji_recycler)

        fun setState(emojiArrayId: Int, emojiClickListener: EmojiAdapter.OnEmojiClickListener?) {
            runOnWorkThread {
                val emojiArray = itemView.resources.getStringArray(emojiArrayId)
                // Log.e(TAG, "setState: ------->emojiArray=$emojiArray   ${emojiArray.size}")
                if (mEmojiList.isEmpty()) {
                    var emoji: Emoji
                    emojiArray.asSequence().forEach {
                        emoji = Emoji(it)
                        mEmojiList.add(emoji)
                    }
                }
                runOnMainThread({
                    emojiRecyclerPageList.setState(mEmojiList, emojiClickListener)
                })
            }
        }
    }

    private val mItems = mutableListOf<Int>()

    private var mOnEmojiClickListener: EmojiAdapter.OnEmojiClickListener? = null

    fun setOnEmojiClickListener(onEmojiClickListener: EmojiAdapter.OnEmojiClickListener) {
        this.mOnEmojiClickListener = onEmojiClickListener
    }

    init {
        mItems.add(R.array.people)
        mItems.add(R.array.objects)
        mItems.add(R.array.nature)
        mItems.add(R.array.places)
        mItems.add(R.array.symbols)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(LayoutInflater.from(parent.context).inflate(R.layout.lay_item_emoji_recycer, parent, false))
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.setState(mItems[position],mOnEmojiClickListener)
    }
}