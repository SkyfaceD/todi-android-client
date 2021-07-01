package org.skyfaced.todi.ui.home.adapter

import androidx.recyclerview.widget.RecyclerView
import org.skyfaced.todi.databinding.ItemHomeBinding
import org.skyfaced.todi.models.task.Task

class HomeViewHolder(private val binding: ItemHomeBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(task: Task, onClick: (task: Task) -> Unit) {
        binding.txtTitle.text = task.title
        binding.txtDescription.text = task.description
        binding.txtCreatedAt.text = task.createdAt

        binding.root.setOnClickListener { onClick(task) }
    }
}