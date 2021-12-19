package com.test.imagesapp.functions

import com.squareup.picasso.Callback
import java.lang.Exception

inline fun picassoCallback(crossinline onSuccess: () -> Unit) = object : Callback {
    override fun onSuccess() {
        onSuccess()
    }

    override fun onError(e: Exception?) {

    }
}