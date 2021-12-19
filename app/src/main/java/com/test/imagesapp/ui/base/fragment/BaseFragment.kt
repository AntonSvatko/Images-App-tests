package com.test.imagesapp.ui.base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.test.imagesapp.R
import com.test.imagesapp.functions.setBlackStatusBar
import com.test.imagesapp.functions.setDefaultStatusBar
import com.test.imagesapp.ui.main.MainActivity
import java.lang.IllegalArgumentException


abstract class BaseFragment<T : ViewDataBinding>(private val resId: Int) : Fragment() {

    protected lateinit var binding: T

    private val mainActivity: MainActivity
        get() = requireActivity() as MainActivity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, resId, container, false
        )

        binding.lifecycleOwner = this
        setupBinding(binding)

        return binding.root
    }

    abstract fun setupBinding(binding: T)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (fragmentType == FragmentType.DETAILS) setupDetailedFragment()
    }

    private fun setupDetailedFragment() {
        setBlackStatusBar()
        mainActivity.hideUI()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (fragmentType == FragmentType.DETAILS) setupDefaultFragment()
    }

    private fun setupDefaultFragment() {
        setDefaultStatusBar()
        mainActivity.showUI()
    }

    protected val requirePostpone: Boolean
        get() = mainActivity.requirePostpone.also {
            mainActivity.requirePostpone = false
        }

    protected val fragmentType: Int
        get() = when (resId) {
            R.layout.fragment_popular -> FragmentType.POPULAR
            R.layout.fragment_favorites -> FragmentType.FAVORITES
            R.layout.fragment_details -> FragmentType.DETAILS
            else -> throw IllegalArgumentException("Add correct resource id.")
        }

    object FragmentType {
        const val POPULAR = 0b001
        const val FAVORITES = 0b010
        const val DETAILS = 0b100
    }

}