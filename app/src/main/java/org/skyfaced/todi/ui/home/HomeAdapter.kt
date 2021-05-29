package org.skyfaced.todi.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.skyfaced.todi.databinding.ItemHomeBinding
import org.skyfaced.todi.utils.extensions.randomString
import org.skyfaced.todi.utils.extensions.toDate

class HomeAdapter(private val onClick: (dummyData: DummyData) -> Unit) :
    RecyclerView.Adapter<HomeAdapter.ViewHolder>() {
    //FIXME replace
    class DummyData(
        val title: String,
        val description: String,
        val timestamp: Long
    )

    private val dummyList = List(20) {
        DummyData(
            "My Awesome ${randomString(10)}",
            randomString(100),
            System.currentTimeMillis()
        )
    }

    class ViewHolder(private val binding: ItemHomeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(dummyData: DummyData, onClick: (dummyData: DummyData) -> Unit) {
            binding.txtTitle.text = dummyData.title
            binding.txtDescription.text = dummyData.description
            binding.txtTimestamp.text = dummyData.timestamp.toDate()

            binding.root.setOnClickListener {
                onClick(dummyData)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemHomeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dummyList[position], onClick)
    }

    override fun getItemCount(): Int {
        return dummyList.size
    }
}