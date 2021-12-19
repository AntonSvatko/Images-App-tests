package com.test.imagesapp.functions

import android.view.View
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import java.util.concurrent.TimeUnit

fun Fragment.postponeForView(view: View) {
    postponeEnterTransition(2, TimeUnit.SECONDS)
    view.doOnPreDraw {
        startPostponedEnterTransition()
    }
}

