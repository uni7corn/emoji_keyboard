package com.example.emojikeyboard

import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.effective.android.panel.PanelSwitchHelper
import com.effective.android.panel.interfaces.listener.OnEditFocusChangeListener
import com.effective.android.panel.interfaces.listener.OnKeyboardStateListener
import com.effective.android.panel.interfaces.listener.OnPanelChangeListener
import com.effective.android.panel.interfaces.listener.OnViewClickListener
import com.effective.android.panel.view.PanelView
import com.example.emojikeyboard.keyboard.chat.ChatAdapter
import com.example.emojikeyboard.keyboard.chat.ChatInfo
import com.example.emojikeyboard.keyboard.emoji.Emoji
import com.example.emojikeyboard.keyboard.emoji.EmojiAdapter
import com.example.emojikeyboard.keyboard.emoji.EmojiRegexUtil
import com.example.emojikeyboard.keyboard.input.OnChatInputViewListener
import com.purple.square.biz.msg.chat.widget.emoji.EmojiPanelView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), EmojiAdapter.OnEmojiClickListener, OnChatInputViewListener {

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }

    private var mLinearLayoutManager: LinearLayoutManager = LinearLayoutManager(this)
    private lateinit var mHelper: PanelSwitchHelper
    private var mAdapter = ChatAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mLinearLayoutManager = LinearLayoutManager(this)
        recycler.layoutManager = mLinearLayoutManager
        mAdapter = ChatAdapter(this, 4)
        recycler.adapter = mAdapter
        chat_input_view.setOnChatInputViewListener(this)
        initEmotionKeyBoard()
    }

    private fun initEmotionKeyBoard() {
        mHelper = PanelSwitchHelper.Builder(this.window, this.window.decorView) //可选
            .addKeyboardStateListener(object : OnKeyboardStateListener {
                override fun onKeyboardChange(visible: Boolean) {
                    Log.d(TAG, "系统键盘是否可见 : $visible")
                    if (visible) {
                        scrollToBottom()
                    }
                }
            })
            .addEditTextFocusChangeListener(object : OnEditFocusChangeListener {
                override fun onFocusChange(view: View?, hasFocus: Boolean) {
                    Log.d(TAG, "输入框是否获得焦点 : $hasFocus")
                    if (hasFocus) {
                        scrollToBottom()
                    }
                }
            }) //可选
            .addViewClickListener(
                object : OnViewClickListener {
                    override fun onClickBefore(view: View?) {
                        Log.d(TAG, "点击了View : $view")
                    }
                }) //可选
            .addPanelChangeListener(
                object : OnPanelChangeListener {
                    override fun onKeyboard() {
                        Log.d(TAG, "唤起系统输入法")
                        chat_input_view.getIvEmoji().isSelected = false
                        scrollToBottom()
                    }

                    override fun onNone() {
                        Log.d(TAG, "隐藏所有面板")
                        chat_input_view.getIvEmoji().isSelected = false
                    }

                    override fun onPanel(view: PanelView?) {
                        Log.d(TAG, "唤起面板 : $view")
                        chat_input_view.getIvEmoji().isSelected = view?.id == R.id.panel_emotion
                        chat_input_view.hideAudioButton()
                        scrollToBottom()
                    }

                    override fun onPanelSizeChange(
                        panelView: PanelView,
                        portrait: Boolean,
                        oldWidth: Int,
                        oldHeight: Int,
                        width: Int,
                        height: Int
                    ) {
                        when (panelView.id) {
                            R.id.panel_emotion -> (panelView.getChildAt(0) as EmojiPanelView).setOnEmojiClickListener(this@MainActivity)
                            R.id.panel_addition -> {

                            }
                        }
                    }
                })
            .contentCanScrollOutside(false)
            .logTrack(true) //output log
            .build()
        recycler.setOnResetPanel {
            mHelper.hookSystemBackByPanelSwitcher()
        }
    }

    override fun onBackPressed() {
        if (mHelper.hookSystemBackByPanelSwitcher()) {
            return
        }
        super.onBackPressed();

    }

    private fun scrollToBottom() {
        runOnUiThread {
            if (mAdapter.itemCount <= 0) return@runOnUiThread
            recycler.scrollToPosition(mAdapter.itemCount - 1)
        }
    }

    override fun onEmojiClick(emoji: Emoji, position: Int) {
        chat_input_view.etInputView().append(emoji.emojiContent)
    }

    override fun onEmojiDelete(emoji: Emoji, position: Int) {
        //获取坐标位置及文本内容
        val etInputView = chat_input_view.etInputView()
        val index = etInputView.selectionStart
        val edit = etInputView.editableText

        //当点击删除按钮时text为-1
        if (emoji.emojiContent == "-1") {
            val str = etInputView.text.toString().trim()
            if (str != "") {
                //只有一个字符
                if (str.length < 2) {
                    etInputView.text.delete(index - 1, index)
                } else if (index > 0) {
                    val lastText = str.substring(index - 2, index)
                    //检测最后两个字符是否为一个emoji(emoji可能存在一个字符的情况 需要进行正则校验)
                    if (EmojiRegexUtil.checkEmoji(lastText)) {
                        etInputView.text.delete(index - 2, index)
                    } else {
                        etInputView.text.delete(index - 1, index)
                    }
                }

            }
        } else {
            //插入你内容
            if (index < 0 || index >= edit.length) {
                edit.append(emoji.emojiContent)
            } else {
                edit.insert(index, emoji.emojiContent)
            }
        }
    }

    override fun onRecordVoice(v: View, event: MotionEvent): Boolean {
        return true
    }

    override fun onClickVoice(v: View) {
        mHelper.hookSystemBackByPanelSwitcher()
    }

    override fun onSendMsg(msg: String) {
        mAdapter.insertInfo(ChatInfo(msg, true))
        scrollToBottom()
    }
}