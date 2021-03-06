package org.skyfaced.todi.ui.home

import android.content.res.Configuration
import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import org.koin.android.ext.android.inject
import org.skyfaced.todi.R
import org.skyfaced.todi.databinding.FragmentHomeBinding
import org.skyfaced.todi.utils.SPAN_COUNT_LANDSCAPE
import org.skyfaced.todi.utils.SPAN_COUNT_PORTRAIT

class HomeFragment : Fragment(R.layout.fragment_home) {
    private val binding by viewBinding(FragmentHomeBinding::bind)
    private val viewModel: HomeViewModel by inject()

    private lateinit var adapter: HomeAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            setupAdapter()
            setupButton()
        }
    }

    @Suppress("MoveVariableDeclarationIntoWhen")
    private fun FragmentHomeBinding.setupAdapter() {
        val orientation = requireActivity().resources.configuration.orientation

        adapter = HomeAdapter { dummyData ->
            val direction = HomeFragmentDirections.actionHomeFragmentToDetailFragment()
            findNavController().navigate(direction)
        }

        recycler.adapter = adapter
        recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                val layoutManager = recyclerView.layoutManager
                val firstVisibleItemPosition = when (layoutManager) {
                    is StaggeredGridLayoutManager ->
                        layoutManager.findFirstCompletelyVisibleItemPositions(null)[0]
                    is GridLayoutManager ->
                        layoutManager.findFirstVisibleItemPosition()
                    is LinearLayoutManager ->
                        layoutManager.findFirstVisibleItemPosition()
                    else -> throw IllegalArgumentException("Can't define layout manager $layoutManager")
                }

                viewModel.firstVisibleItemPosition = firstVisibleItemPosition
            }
        })

        configureRecyclerView(orientation)
    }

    private fun FragmentHomeBinding.configureRecyclerView(orientation: Int) {
        recycler.layoutManager = StaggeredGridLayoutManager(
            if (orientation == ORIENTATION_LANDSCAPE) SPAN_COUNT_LANDSCAPE else SPAN_COUNT_PORTRAIT,
            StaggeredGridLayoutManager.VERTICAL
        )
    }

    private fun FragmentHomeBinding.setupButton() {
        btnSettings.setOnClickListener {
            val direction = HomeFragmentDirections.actionHomeFragmentToSettingsFragment()
            findNavController().navigate(direction)
        }

        btnAddTask.setOnClickListener {
            val direction = HomeFragmentDirections.actionHomeFragmentToDetailFragment()
            findNavController().navigate(direction)
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        with(binding) {
            configureRecyclerView(newConfig.orientation)
            recycler.scrollToPosition(viewModel.firstVisibleItemPosition)
        }
    }
}