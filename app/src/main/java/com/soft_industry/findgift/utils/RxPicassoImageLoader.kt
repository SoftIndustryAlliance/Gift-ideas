package com.soft_industry.findgift.utils

import android.widget.ImageView
import com.squareup.picasso.Picasso
import io.reactivex.functions.Consumer

class RxPicassoImageLoader(val imgView: ImageView) : Consumer<String> {
    override fun accept(imageUrl: String?) {
        Picasso.get()
                .load(imageUrl)
                .fit()
                .centerCrop()
                .into(imgView)
    }

}