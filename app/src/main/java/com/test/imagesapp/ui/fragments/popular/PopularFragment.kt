package com.test.imagesapp.ui.fragments.popular

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.test.imagesapp.R
import com.test.imagesapp.data.model.Photo
import com.test.imagesapp.data.model.sizes.Size
import com.test.imagesapp.data.model.sizes.SizesParcelableData
import com.test.imagesapp.databinding.FragmentPopularBinding
import com.test.imagesapp.functions.*
import com.test.imagesapp.ui.adapter.ImagesAdapter
import com.test.imagesapp.ui.base.fragment.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PopularFragment : BaseFragment<FragmentPopularBinding>(R.layout.fragment_popular) {

    @Inject
    lateinit var adapter: ImagesAdapter

    @Inject
    lateinit var manager: LocalBroadcastManager

    private val receiver = photoReceiver(::handleReceiver)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        manager.registerReceiver(receiver, defaultFilter)
    }

    override fun setupBinding(binding: FragmentPopularBinding) {
        val viewModel: PopularViewModel by requireActivity().viewModels()
        binding.viewModel = viewModel

        binding.adapter = adapter.withFragmentType(fragmentType)

        if (requirePostpone)
            postponeForView(binding.popularRecyclerView)
    }

    private fun handleReceiver(intent: Intent) {
        when (intent.action) {
            SIZES_ACTION -> intent.getParcelableExtra<SizesParcelableData>(SIZES_EXTRA)?.let {
                adapter.setSizesForPhoto(it)
            }
            PHOTO_ACTION -> intent.getParcelableExtra<Photo>(PHOTO_EXTRA)?.let {
                adapter.setNewFavorite(it)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        manager.unregisterReceiver(receiver)
    }


}