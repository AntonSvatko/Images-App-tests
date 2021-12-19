package com.test.imagesapp.binding

import android.graphics.drawable.BitmapDrawable
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso
import com.test.imagesapp.R
import com.test.imagesapp.data.model.Photo
import com.test.imagesapp.data.model.sizes.Label
import com.test.imagesapp.data.model.sizes.Size
import com.test.imagesapp.functions.interfaces.OnSavedChanged
import com.test.imagesapp.functions.picassoCallback
import com.test.imagesapp.network.exceptions.NoConnectivityException
import com.test.imagesapp.ui.base.fragment.BaseFragment
import com.test.imagesapp.ui.views.FavoriteImage

@BindingAdapter("isProgress")
fun bindProcess(progressBar: ProgressBar, isProgress: Boolean) {
    progressBar.visibility = if (isProgress) View.VISIBLE else View.GONE
}

@BindingAdapter("photo", "picasso", "isDetails", "sizes", requireAll = false)
fun bindPhotoImage(
    imageView: ImageView,
    photo: Photo,
    picasso: Picasso,
    isDetails: Boolean = false,
    sizes: List<Size>? = null
) {

    if (isDetails) {
        val neededPhotoSize = (sizes ?: photo.sizes)?.find { it.labelEnum == Label.MEDIUM }

        val placeholderDrawable =
            BitmapDrawable(imageView.resources, photo.lowQualityPlaceholder)

        picasso.load(neededPhotoSize?.url).placeholder(placeholderDrawable).into(imageView)

    } else {
        if (imageView is FavoriteImage && photo.photoFragmentType == BaseFragment.FragmentType.POPULAR) {
            imageView.isDrawFavorite = photo.isSaved
        }

        picasso.load(photo.url).into(imageView, picassoCallback {
            (imageView as? FavoriteImage)?.isImageDrawn = true
        })
    }

}

@BindingAdapter("saved", "listener")
fun bindSavedPhoto(imageView: ImageView, isSaved: Boolean, listener: OnSavedChanged) {
    val drawableId = if (isSaved) R.drawable.ic_favorites else R.drawable.ic_favorites_outline
    val drawable = ContextCompat.getDrawable(imageView.context, drawableId)

    listener.onChange(isSaved)

    imageView.setImageDrawable(drawable)
}

@BindingAdapter("toast")
fun bindFailedToast(progressBar: ProgressBar, throwable: Throwable?) {
    throwable?.let {
        val context = progressBar.context
        val resources = context.resources

        val text = when (it) {
            is NoConnectivityException -> resources.getString(R.string.failed_connection)
            else -> resources.getString(R.string.failed_to_load)
        }

        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }
}
