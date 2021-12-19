    package com.test.imagesapp.ui.fragments.popular

import androidx.databinding.ObservableBoolean
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.test.imagesapp.data.model.Photo
import com.test.imagesapp.data.repository.DataRepository
import com.test.imagesapp.functions.createCoroutineHandler
import com.test.imagesapp.network.result.ResultOf
import com.test.imagesapp.ui.base.viewmodel.CoroutineViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class PopularViewModel @ViewModelInject constructor(
    private val repository: DataRepository
) : CoroutineViewModel() {

    val isLoading = ObservableBoolean()

    val photosLiveData: MutableLiveData<List<Photo>> = MutableLiveData()

    val toastFailedLiveData = MutableLiveData<Throwable?>()

    private val favoritesHashMap = hashMapOf<String, Boolean>()

    init {
        isLoading.set(true)

        launchSafely(onError = {
            toastFailedLiveData.value = it
            isLoading.set(false)
        }) {
            getFavorites()
            getPhotos()
        }
    }

    private suspend fun getFavorites() {
        val favorites = repository.getFavoritesIds()

        favorites.forEach {
            favoritesHashMap[it] = true
        }
    }

    private suspend fun getPhotos() {
        repository.fetchPhotos().collect { result ->
            val value = when (result) {
                is ResultOf.Success -> result.data
                is ResultOf.Failure -> {
                    toastFailedLiveData.value = result.throwable
                    emptyList()
                }
            }

            photosLiveData.value = mutableListOf<Photo>().apply {
                photosLiveData.value?.let { addAll(it) }
                validateFavoritePhotos(value)
                addAll(value)
            }

            isLoading.set(false)
        }
    }

    private fun validateFavoritePhotos(photos: List<Photo>) {
        photos.forEach {
            if (favoritesHashMap[it.id] != null)
                it.isSaved = true
        }
    }

}