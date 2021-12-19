package com.test.imagesapp.ui.fragments.details

import android.util.Log
import androidx.databinding.Observable
import androidx.databinding.ObservableBoolean
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.squareup.picasso.Picasso
import com.test.imagesapp.data.model.Photo
import com.test.imagesapp.data.model.sizes.Size
import com.test.imagesapp.data.model.sizes.Sizes
import com.test.imagesapp.data.repository.DataRepository
import com.test.imagesapp.functions.createCoroutineHandler
import com.test.imagesapp.network.result.ResultOf
import com.test.imagesapp.ui.base.viewmodel.CoroutineViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DetailsViewModel @ViewModelInject constructor(
    private val dataRepository: DataRepository,
    val picasso: Picasso
) : CoroutineViewModel() {

    val isFavorite = ObservableBoolean()

    var photoSizesLiveData: MutableLiveData<List<Size>> = MutableLiveData()

    fun setFavorite(isSaved: Boolean) {
        isFavorite.set(isSaved)
    }

    fun handlePhoto(photo: Photo) = launchSafely {
        val isSaved = dataRepository.handleFavorite(photo)
        isFavorite.set(isSaved)
    }

    fun getPhotoSizes(photo: Photo) = launchSafely {
        dataRepository.fetchSizes(photo.id).collect {
            photoSizesLiveData.value = when (it) {
                is ResultOf.Success -> {
                    photo.sizes = it.data
                    handleSizes(photo)
                    photo.sizes
                }
                is ResultOf.Failure -> emptyList()
            }
        }
    }

    private fun handleSizes(photo: Photo) = launchSafely {
        if (dataRepository.getFavorite(photo.id) != null)
            dataRepository.updateFavorite(photo)
    }
}