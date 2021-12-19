package com.test.imagesapp.ui.fragments.details

import android.util.Log
import android.view.View
import androidx.databinding.Observable
import androidx.fragment.app.viewModels
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.test.imagesapp.R
import com.test.imagesapp.databinding.FragmentDetailsBinding
import com.test.imagesapp.functions.createPhotoIntent
import com.test.imagesapp.functions.createSizesIntent
import com.test.imagesapp.functions.interfaces.OnSavedChanged
import com.test.imagesapp.functions.validate
import com.test.imagesapp.ui.base.fragment.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailsFragment : BaseFragment<FragmentDetailsBinding>(R.layout.fragment_details) {

    @Inject
    lateinit var localManager: LocalBroadcastManager

    private val navArgs: DetailsFragmentArgs by navArgs()

    override fun setupBinding(binding: FragmentDetailsBinding) {
        val viewModel: DetailsViewModel by viewModels()
        val photo = navArgs.photo.validate()

        val onClickListener = View.OnClickListener {
            viewModel.handlePhoto(photo)
            viewModel.isFavorite
        }

        val onBackClickListener = View.OnClickListener {
            it.findNavController().popBackStack()
        }

        val onSavedChangeListener = OnSavedChanged {
            localManager.sendBroadcast(createPhotoIntent(photo))
        }

        if (photo.sizes.isNullOrEmpty()){
            viewModel.photoSizesLiveData.observe(this) {
                localManager.sendBroadcast(createSizesIntent(photo, it))
            }
            viewModel.getPhotoSizes(photo)
        }

        viewModel.setFavorite(photo.isSaved)

        binding.photo = photo
        binding.viewModel = viewModel
        binding.onClickListener = onClickListener
        binding.onBackClickListener = onBackClickListener
        binding.onSavedChangeListener = onSavedChangeListener
    }

}