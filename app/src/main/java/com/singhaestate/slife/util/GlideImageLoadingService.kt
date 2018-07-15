package com.singhaestate.slife.util

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import ss.com.bannerslider.ImageLoadingService

class GlideImageLoadingService(context: Context?) : ImageLoadingService {

    private val context: Context? = context

    override fun loadImage(url: String?, imageView: ImageView?) {
        imageView?.let { context?.let { it1 -> Glide.with(it1).load(url).into(it) } }
    }

    override fun loadImage(resource: Int, imageView: ImageView?) {
        imageView?.let { context?.let { it1 -> Glide.with(it1).load(resource).into(it) } }
    }

    override fun loadImage(url: String?, placeHolder: Int, errorDrawable: Int, imageView: ImageView?) {
        imageView?.let {
            it.setImageResource(placeHolder)
            context?.let { it1 -> Glide.with(it1).load(url).into(imageView) }
        }
    }
}