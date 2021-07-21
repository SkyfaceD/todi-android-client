package org.skyfaced.todi.ui.home

import android.content.res.Configuration
import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.skyfaced.todi.R
import org.skyfaced.todi.databinding.FragmentHomeBinding
import org.skyfaced.todi.utils.SPAN_COUNT_LANDSCAPE
import org.skyfaced.todi.utils.SPAN_COUNT_PORTRAIT
import org.skyfaced.todi.utils.extensions.firstVisibleItemPosition

class HomeFragment : Fragment(R.layout.fragment_home) {
    private val binding by viewBinding(FragmentHomeBinding::bind)
    private val viewModel: HomeViewModel by viewModel()

    private lateinit var adapter: HomeAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            setupAdapter()
            setupButton()

            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.userPreferencesId.flowWithLifecycle(
                    viewLifecycleOwner.lifecycle,
                    Lifecycle.State.CREATED
                ).collect { id ->
                    Log.d(TAG, "onViewCreated: id - $id")
                }

                viewModel.tasks.flowWithLifecycle(
                    viewLifecycleOwner.lifecycle,
                    Lifecycle.State.CREATED
                ).collect { tasks ->
                    adapter.submitList(tasks)
                }
            }
        }
    }

    @Suppress("MoveVariableDeclarationIntoWhen")
    private fun FragmentHomeBinding.setupAdapter() {
        val orientation = requireActivity().resources.configuration.orientation

        adapter = HomeAdapter {
            val direction = HomeFragmentDirections.actionHomeFragmentToDetailFragment()
            findNavController().navigate(direction)
        }

        recycler.adapter = adapter
        recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                val layoutManager = recyclerView.layoutManager
                viewModel.firstVisibleItemPosition = layoutManager?.firstVisibleItemPosition ?: 0
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

    companion object {
        private const val TAG = "HomeFragment"
    }
}