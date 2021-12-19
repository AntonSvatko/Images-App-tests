package com.test.imagesapp.ui.views

import android.content.Context
import android.graphics.Canvas
import android.media.Image
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView

class SquareLayout(context: Context, attributeSet: AttributeSet) :
    FrameLayout(context, attributeSet) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(
            widthMeasureSpec,
            widthMeasureSpec
        )
    }


}