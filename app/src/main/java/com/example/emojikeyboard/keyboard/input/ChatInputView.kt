package com.example.emojikeyboard.keyboard.input

import android.content.Context
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.core.widget.doAfterTextChanged
import com.blankj.utilcode.util.KeyboardUtils
import com.example.emojikeyboard.R
import kotlinx.android.synthetic.main.lay_chat_input_view.view.*

/**
 *  Created by sai
 *  on 2020/4/14
 *
 *  desc:  聊天软键盘输入框
 */
class ChatInputView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    FrameLayout(context, attrs, defStyleAttr), TextView.OnEditorActionListener {

    companion object {
        private val TAG = ChatInputView::class.java.simpleName
    }

    private var mOnChatInputViewListener: OnChatInputViewListener? = null

    init {
        View.inflate(context, R.layout.lay_chat_input_view, this)
        iv_voice.setOnClickListener {
            if (it.tag == null) {
                it.tag = true
                showAudioButton()
                mOnChatInputViewListener?.onClickVoice(it)
                KeyboardUtils.hideSoftInput(etInputView())
            } else {
                it.tag = null
                hideAudioButton()
                KeyboardUtils.showSoftInput(etInputView())
            }
        }

        et_msg.imeOptions = EditorInfo.IME_ACTION_SEND
        et_msg.setOnEditorActionListener(this)
        et_msg.doAfterTextChanged {
            if (et_msg.text.toString().trim().isBlank()) {
                btn_send_msg.visibility = View.GONE
                iv_more.visibility = View.VISIBLE
            } else {
                btn_send_msg.visibility = View.VISIBLE
                iv_more.visibility = View.GONE
            }
        }

        tv_record_voice.setOnTouchListener { v, event ->
            return@setOnTouchListener mOnChatInputViewListener?.onRecordVoice(v, event) ?: false
        }
//        iv_face.setOnClickListener {
//            mOnChatInputViewListener?.onFacePanelClick()
//        }
//        iv_more.setOnClickListener {
//            mOnChatInputViewListener?.onMorePanelClick()
//        }
        btn_send_msg.setOnClickListener {
            val inputMsg = et_msg.text.toString().trim()
            et_msg.text = null
            mOnChatInputViewListener?.onSendMsg(inputMsg)
        }
    }

    fun etInputView(): EditText {
        return et_msg
    }

    fun getIvEmoji(): ImageView {
        return iv_face
    }

    fun getIvMore(): ImageView {
        return iv_more
    }

    fun getIvVoice(): ImageView {
        return iv_voice
    }

    fun setOnChatInputViewListener(onChatInputViewListener: OnChatInputViewListener) {
        this.mOnChatInputViewListener = onChatInputViewListener
    }

    private fun showAudioButton() {
        tv_record_voice.visibility = View.VISIBLE
        iv_voice.setImageResource(R.drawable.ic_chat_text_keyboard)
    }

    fun hideAudioButton() {
        tv_record_voice.visibility = View.GONE
        iv_voice.setImageResource(R.drawable.ic_chat_voice)
        iv_voice.tag = null
    }

    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        when (actionId) {
            EditorInfo.IME_ACTION_SEND -> {
                mOnChatInputViewListener?.onSendMsg(v?.text.toString().trim())
                et_msg.setText("")
            }
        }
        return true
    }
}

interface OnChatInputViewListener {
    fun onRecordVoice(v: View, event: MotionEvent): Boolean
    fun onClickVoice(v: View)
    fun onSendMsg(msg: String)
}