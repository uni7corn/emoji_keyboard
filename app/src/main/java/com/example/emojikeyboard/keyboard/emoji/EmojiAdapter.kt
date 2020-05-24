package com.example.emojikeyboard.keyboard.emoji

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.emojikeyboard.R

/**
 *  Created by sai
 *  on 2020/5/17
 *
 *  desc:
 */
class EmojiAdapter : RecyclerView.Adapter<EmojiAdapter.VH>() {

    private val mItems = mutableListOf<Emoji>()
    private var mOnEmojiClickListener: OnEmojiClickListener? = null
    fun setOnEmojiClickListener(onEmojiClickListener: OnEmojiClickListener?) {
        this.mOnEmojiClickListener = onEmojiClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(LayoutInflater.from(parent.context).inflate(R.layout.lay_item_emoji, parent, false))
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val emoji = mItems[position]
        holder.itemView.setOnClickListener {
            mOnEmojiClickListener?.onEmojiClick(emoji, position)
        }
        holder.setState(emoji)
    }

    fun addAllItems(newItems: List<Emoji>) {
        if (mItems.isNotEmpty()) return
        val position = mItems.size
        mItems.addAll(newItems)
        notifyItemRangeInserted(position, newItems.size)
    }

    class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val tvEmoji = itemView.findViewById<TextView>(R.id.tv_emoji)

        fun setState(item: Emoji) {
            tvEmoji.text = item.emojiContent
        }
    }

    interface OnEmojiClickListener {
        fun onEmojiClick(emoji: Emoji, position: Int)
        fun onEmojiDelete(emoji: Emoji, position: Int) {}
    }
}