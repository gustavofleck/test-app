package com.gustavofleck.sicrediteste.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gustavofleck.domain.models.SimplifiedEvent
import com.gustavofleck.sicrediteste.databinding.EventItemBinding
import com.gustavofleck.sicrediteste.viewholders.EventListViewHolder

class EventListAdapter(
    private val onClick: (String) -> Unit
): ListAdapter<SimplifiedEvent, EventListViewHolder>(EventListAdapter) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventListViewHolder {
        val binding = EventItemBinding.inflate(LayoutInflater.from(parent.context))
        return EventListViewHolder(binding, parent.context)
    }

    override fun onBindViewHolder(holder: EventListViewHolder, position: Int) {
        holder.bind(getItem(position), onClick)
    }

    private companion object : DiffUtil.ItemCallback<SimplifiedEvent>() {
        override fun areItemsTheSame(oldItem: SimplifiedEvent, newItem: SimplifiedEvent): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: SimplifiedEvent, newItem: SimplifiedEvent): Boolean {
            return oldItem == newItem
        }

    }
}


