package com.soft_industry.findgift.utils

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import com.jakewharton.rxrelay2.PublishRelay
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import io.reactivex.Observable
import java.lang.Exception

/**
 * Created by user on 3/22/18.
 */
class BitmapLoader {
    val relay: PublishRelay<Bitmap> by lazy { PublishRelay.create<Bitmap>() }
    fun load(url: String) :Observable<Bitmap>{
        val target = object : Target {
            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {}

            override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {}

            override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                relay.accept(bitmap)
            }
        }
       Picasso.get()
                .load(url)
                .into(target)
        return relay
                .take(1)
    }
}