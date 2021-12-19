package com.test.imagesapp.ui.fragments.favorites

import android.util.Log
import androidx.databinding.ObservableBoolean
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.test.imagesapp.data.model.Photo
import com.test.imagesapp.data.repository.DataRepository
import com.test.imagesapp.ui.base.viewmodel.CoroutineViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoritesViewModel @ViewModelInject constructor(
    private val repository: DataRepository
) : CoroutineViewModel() {

    val isLoading = ObservableBoolean()

    var photosLiveData: LiveData<List<Photo>> = MutableLiveData()

    init {
        isLoading.set(true)
        launchSafely { getPhotos() }
    }

    private suspend fun getPhotos() {
        photosLiveData = liveData(Dispatchers.IO) {
            emitSource(repository.getFavoritesLiveData())
            isLoading.set(false)
        }
    }

}