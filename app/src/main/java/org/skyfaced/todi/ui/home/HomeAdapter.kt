package org.skyfaced.todi.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.skyfaced.todi.databinding.ItemHomeBinding
import org.skyfaced.todi.utils.extensions.randomString
import org.skyfaced.todi.utils.extensions.toDate
import kotlin.random.Random.Default.nextInt
import kotlin.random.Random.Default.nextLong

class HomeAdapter(private val onClick: (dummyData: DummyData) -> Unit) :
    RecyclerView.Adapter<HomeAdapter.ViewHolder>() {
    //FIXME replace
    class DummyData(
        val title: String,
        val description: String,
        val timestamp: Long
    )

    private val dummyList = listOf(
        DummyData(
            "My Awesome Note",
            randomString(nextInt(100)),
            System.currentTimeMillis() - nextLong(1_000_000_000_000)
        ),
        DummyData(
            "Dish",
            randomString(nextInt(100)),
            System.currentTimeMillis() - nextLong(1_000_000_000_000)
        ),
        DummyData(
            "Dinner",
            randomString(nextInt(100)),
            System.currentTimeMillis() - nextLong(1_000_000_000_000)
        ),
        DummyData(
            "Magazine",
            randomString(nextInt(100)),
            System.currentTimeMillis() - nextLong(1_000_000_000_000)
        ),
        DummyData(
            "Buy products",
            randomString(nextInt(100)),
            System.currentTimeMillis() - nextLong(1_000_000_000_000)
        ),
        DummyData(
            "Another note",
            randomString(nextInt(100)),
            System.currentTimeMillis() - nextLong(1_000_000_000_000)
        ),
        DummyData(
            "My Awesome Note",
            randomString(nextInt(100)),
            System.currentTimeMillis() - nextLong(1_000_000_000_000)
        ),
        DummyData(
            "Dish",
            randomString(nextInt(100)),
            System.currentTimeMillis() - nextLong(1_000_000_000_000)
        ),
        DummyData(
            "Dinner",
            randomString(nextInt(100)),
            System.currentTimeMillis() - nextLong(1_000_000_000_000)
        ),
        DummyData(
            "Magazine",
            randomString(nextInt(100)),
            System.currentTimeMillis() - nextLong(1_000_000_000_000)
        ),
        DummyData(
            "Buy products",
            randomString(nextInt(100)),
            System.currentTimeMillis() - nextLong(1_000_000_000_000)
        ),
        DummyData(
            "Another note",
            randomString(nextInt(100)),
            System.currentTimeMillis() - nextLong(1_000_000_000_000)
        ),
        DummyData(
            "My Awesome Note",
            randomString(nextInt(100)),
            System.currentTimeMillis() - nextLong(1_000_000_000_000)
        ),
        DummyData(
            "Dish",
            randomString(nextInt(100)),
            System.currentTimeMillis() - nextLong(1_000_000_000_000)
        ),
        DummyData(
            "Dinner",
            randomString(nextInt(100)),
            System.currentTimeMillis() - nextLong(1_000_000_000_000)
        ),
        DummyData(
            "Magazine",
            randomString(nextInt(100)),
            System.currentTimeMillis() - nextLong(1_000_000_000_000)
        ),
        DummyData(
            "Buy products",
            randomString(nextInt(100)),
            System.currentTimeMillis() - nextLong(1_000_000_000_000)
        ),
        DummyData(
            "Another note",
            randomString(nextInt(100)),
            System.currentTimeMillis() - nextLong(1_000_000_000_000)
        )
    )

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