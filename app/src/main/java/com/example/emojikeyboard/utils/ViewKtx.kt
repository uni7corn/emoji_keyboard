@file:Suppress("DEPRECATION")

package com.example.emojikeyboard.utils

import android.app.Activity
import android.content.res.TypedArray
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.widget.Checkable
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.annotation.StyleableRes
import kotlin.math.roundToInt

fun View.parseColor(@ColorRes color: Int): Int {
    return resources.getColor(color)
}

fun Activity.parseColor(@ColorRes color: Int): Int {
    return resources.getColor(color)
}

@JvmOverloads
fun View.parseAttrs(@StyleableRes attrsRes: IntArray, attrs: AttributeSet?, defStyleAttr: Int = 0, attrsBlock: (TypedArray) -> Unit) {
    val attributes = context.obtainStyledAttributes(attrs, attrsRes, defStyleAttr, 0)
    attrsBlock.invoke(attributes)
    attributes.recycle()
}

fun View.dp2px(dp: Float): Float {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics)
}

fun View.dp2pxInt(dp: Float): Int {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics).roundToInt()
}

fun View.sp2px(sp: Float): Float {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, resources.displayMetrics)
}

fun View.text(@StringRes textId: Int): String {
    return resources.getString(textId)
}

// 扩展点击事件属性(重复点击时长)
var <T : View> T.lastClickTime: Long
    set(value) = setTag(1766613352, value)
    get() = getTag(1766613352) as? Long ?: 0

// 重复点击事件绑定
inline fun <T : View> T.singleClick(time: Long = 200, crossinline block: (T) -> Unit) {
    setOnClickListener {
        val currentTimeMillis = System.currentTimeMillis()
        if (currentTimeMillis - lastClickTime > time || this is Checkable) {
            lastClickTime = currentTimeMillis
            block(this)
        } else {
            // showToast("客官，休息一下呗...")
        }
    }
}
