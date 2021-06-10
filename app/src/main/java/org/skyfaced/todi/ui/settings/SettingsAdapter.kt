package org.skyfaced.todi.ui.settings

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView
import org.skyfaced.todi.R
import org.skyfaced.todi.databinding.ItemSettingsBinding

class SettingsAdapter(private val onClick: (position: Int) -> Unit) :
    RecyclerView.Adapter<SettingsAdapter.ViewHolder>() {
    class Settings(
        @DrawableRes val icon: Int,
        @StringRes val title: Int,
        @StringRes val summary: Int
    )

    private val dummySettings = listOf(
        Settings(
            R.drawable.ic_settings_decor,
            R.string.settings_decor_title,
            R.string.settings_decor_summary
        )
    )

    class ViewHolder(private val binding: ItemSettingsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(settings: Settings, position: Int, onClick: (position: Int) -> Unit) {
            with(binding) {
                icon.setImageResource(settings.icon)
                title.setText(settings.title)
                summary.setText(settings.summary)

                root.setOnClickListener { onClick(position) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemSettingsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dummySettings[position], position, onClick)
    }

    override fun getItemCount(): Int {
        return dummySettings.size
    }
}