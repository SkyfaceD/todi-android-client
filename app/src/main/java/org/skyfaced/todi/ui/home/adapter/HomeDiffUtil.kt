package org.skyfaced.todi.ui.home.adapter

import androidx.recyclerview.widget.DiffUtil
import org.skyfaced.todi.models.task.Task

open class HomeDiffUtil : DiffUtil.ItemCallback<Task>() {
    override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem::class != newItem::class
    }

    override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem::class != newItem::class
    }

    override fun getChangePayload(oldItem: Task, newItem: Task): Any? {
        return oldItem::class != newItem::class
    }

    companion object : HomeDiffUtil()
}