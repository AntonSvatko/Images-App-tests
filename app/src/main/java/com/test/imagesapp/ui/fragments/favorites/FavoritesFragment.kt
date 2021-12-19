package com.test.imagesapp.ui.fragments.favorites

import androidx.activity.viewModels
import com.test.imagesapp.R
import com.test.imagesapp.databinding.FragmentFavoritesBinding
import com.test.imagesapp.functions.postponeForView
import com.test.imagesapp.ui.adapter.ImagesAdapter
import com.test.imagesapp.ui.base.fragment.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FavoritesFragment : BaseFragment<FragmentFavoritesBinding>(R.layout.fragment_favorites) {

    @Inject
    lateinit var adapter: ImagesAdapter

    override fun setupBinding(binding: FragmentFavoritesBinding) {
        val viewModel: FavoritesViewModel by requireActivity().viewModels()
        binding.viewModel = viewModel
        binding.adapter = adapter.withFragmentType(fragmentType)

        if(requirePostpone)
            postponeForView(binding.favoritesRecyclerView)
    }
}