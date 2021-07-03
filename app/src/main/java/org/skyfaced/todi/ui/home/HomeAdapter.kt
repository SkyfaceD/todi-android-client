package org.skyfaced.todi.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.skyfaced.todi.databinding.ItemHomeBinding
import org.skyfaced.todi.models.task.Task

class HomeAdapter(
    private val onClick: (task: Task) -> Unit
) : ListAdapter<Task, HomeAdapter.HomeViewHolder>(HomeDiffUtil()) {
    class HomeViewHolder(private val binding: ItemHomeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(task: Task, onClick: (task: Task) -> Unit) {
            binding.txtTitle.text = task.title
            binding.txtDescription.text = task.description
            binding.txtCreatedAt.text = task.createdAt

            binding.root.setOnClickListener { onClick(task) }
        }
    }

    class HomeDiffUtil : DiffUtil.ItemCallback<Task>() {
        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem == newItem
        }
    }

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