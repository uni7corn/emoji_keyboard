package com.example.emojikeyboard.utils

import android.view.View
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions


/**
 * 拓展函数  可以更加方便的使用并加载图片   用法为 kotlin: ImageView().loadImage()
 *                                             java:  ImageLoaderKt.loadImage()
 *
 * @receiver ImageView
 * @param drawableId Int
 * @param placeHolderId Int
 * @param errorId Int
 * @param isCenterCrop Boolean
 */
@JvmOverloads
fun ImageView.loadImage(
    @DrawableRes drawableId: Int = -1,
    @DrawableRes placeHolderId: Int = -1,
    @DrawableRes errorId: Int = -1,
    isCenterCrop: Boolean = false
) {

    var options = RequestOptions.placeholderOf(placeHolderId).error(errorId)
        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)

    if (isCenterCrop) {
        options = options.centerCrop()
    }

    if (visibility != View.VISIBLE) {
        visibility = View.VISIBLE
    }
    Glide.with(this).load(drawableId).apply(options).skipMemoryCache(false).into(this)
}

/**
 * 拓展函数  可以更加方便的使用并加载图片   用法为 kotlin: ImageView().loadImage()
 *                                             java:  ImageLoaderKt.loadImage()
 * @receiver ImageView
 * @param url String
 * @param placeHolderId Int
 * @param errorId Int
 * @param isCenterCrop Boolean
 */
@JvmOverloads
fun ImageView.loadImage(
    url: String, @DrawableRes placeHolderId: Int = -1, @DrawableRes errorId: Int = -1,
    isCenterCrop: Boolean = false
) {
    var options = RequestOptions.placeholderOf(placeHolderId).error(errorId)
        .diskCacheStrategy(DiskCacheStrategy.ALL).dontAnimate()
        .skipMemoryCache(false)
    if (isCenterCrop) {
        options = options.centerCrop()
    }

    if (visibility != View.VISIBLE) {
        visibility = View.VISIBLE
    }
    Glide.with(this).load(url).apply(options).into(this)
}
