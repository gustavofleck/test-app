package com.gustavofleck.sicrediteste.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.gustavofleck.domain.models.SimplifiedEvent
import com.gustavofleck.sicrediteste.databinding.EventItemBinding

class EventListViewHolder(private val binding: EventItemBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(event: SimplifiedEvent, onClick: (String) -> Unit) {
        with(binding) {
            eventTitle.text = event.title
            eventDate.text = event.date
            eventPrice.text = "R$ ${event.price}"
            eventCard.setOnClickListener { onClick(event.id) }
        }
    }

}