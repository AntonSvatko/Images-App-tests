

package com.test.imagesapp.binding

import android.util.Log
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.test.imagesapp.data.model.Photo
import com.test.imagesapp.ui.adapter.ImagesAdapter
import com.test.imagesapp.ui.decorations.ImageDecoration

@BindingAdapter("adapter", requireAll = true)
fun adapter(view: RecyclerView, adapter: RecyclerView.Adapter<*>){
    view.addItemDecoration(ImageDecoration())
    view.adapter = adapter
}

@BindingAdapter("photosList")
fun photosList(view: RecyclerView, list: List<Photo>?){
    list?.let {
        (view.adapter as? ImagesAdapter)?.setItems(it)
    }
}

