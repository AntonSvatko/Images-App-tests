package com.test.imagesapp.ui.views

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import com.test.imagesapp.R
import kotlin.properties.Delegates
import kotlin.reflect.KProperty


class FavoriteImage(context: Context, attributeSet: AttributeSet) :
    AppCompatImageView(context, attributeSet) {

    private val paintColor = 0xbbcccccc.toInt()

    private val redPaintColor = 0xffbC33938.toInt()

    private val circlePaint = Paint().apply {
        color = paintColor
        style = Paint.Style.FILL
        isAntiAlias = true
    }

    private val redPaint = Paint().apply {
        colorFilter = PorterDuffColorFilter(redPaintColor, PorterDuff.Mode.SRC_IN)
    }

    var isImageDrawn: Boolean by Delegates.observable(false, ::processBoolean)

    var isDrawFavorite: Boolean by Delegates.observable(false, ::processBoolean)

    private val needToDraw: Boolean
        get() = isImageDrawn && isDrawFavorite

    private val favoriteBadgeMultiplier = .3f

    private var favoriteBitmap: Bitmap? = null

    private val favoriteMatrix = Matrix()

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (needToDraw) {
            canvas?.let {
                if (favoriteBitmap == null) {
                    favoriteBitmap = ResourcesCompat.getDrawable(
                        resources,
                        R.drawable.ic_favorites, null
                    )?.toBitmap()
                }

                val radius = measuredWidth * favoriteBadgeMultiplier
                val bitmapHeight = favoriteBitmap!!.height

                val x = measuredWidth - radius / 2 - bitmapHeight / 4
                val y = (radius - bitmapHeight) / 2 - bitmapHeight / 4

                favoriteMatrix.setTranslate(x, y)

                it.drawCircle(measuredWidth.toFloat(), 0f, radius, circlePaint)

                favoriteBitmap?.let { bitmap ->
                    it.drawBitmap(bitmap, favoriteMatrix, redPaint)
                }

            }
        } else recycleDrawing()
    }

    private fun recycleDrawing() {
        favoriteBitmap = null
    }


    private fun processBoolean(p: KProperty<*>, oldValue: Boolean, newValue: Boolean) {
        if (oldValue == newValue) return
        if (needToDraw) invalidate()
    }

}