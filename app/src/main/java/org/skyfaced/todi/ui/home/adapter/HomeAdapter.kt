package org.skyfaced.todi.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import org.skyfaced.todi.databinding.ItemHomeBinding
import org.skyfaced.todi.models.task.Task

class HomeAdapter(
    private val onClick: (task: Task) -> Unit
) : ListAdapter<Task, HomeViewHolder>(HomeDiffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder(
            ItemHomeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bind(getItem(position), onClick)
    }
}