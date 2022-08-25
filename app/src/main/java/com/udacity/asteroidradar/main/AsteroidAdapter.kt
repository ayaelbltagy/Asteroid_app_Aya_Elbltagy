package com.udacity.asteroidradar.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.database.AsteroidEntity
import com.udacity.asteroidradar.databinding.ItemBinding

class AsteroidAdapter(val clickListener: AsteroidClickListener) : ListAdapter<AsteroidEntity, AsteroidAdapter.ViewHolder>(AdapterCallback()) {


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(viewGroup)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        viewHolder.bind(getItem(position)!!, clickListener)

    }

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder private constructor(val binding: ItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: AsteroidEntity, clickListener: AsteroidClickListener) {
            binding.asteroid = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = ItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(view)
            }
        }
    }


}

class AdapterCallback : DiffUtil.ItemCallback<AsteroidEntity>() {
    override fun areItemsTheSame(oldItem: AsteroidEntity, newItem: AsteroidEntity): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: AsteroidEntity, newItem: AsteroidEntity): Boolean {
        return oldItem.id == newItem.id
    }
}

class AsteroidClickListener(val clickListener: (sleepId: Long) -> Unit) {
    fun onClick(asteroid: AsteroidEntity) = clickListener(asteroid.id)
}

