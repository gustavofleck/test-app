package com.gustavofleck.sicrediteste.viewholders

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gustavofleck.domain.models.SimplifiedEvent
import com.gustavofleck.sicrediteste.R
import com.gustavofleck.sicrediteste.databinding.EventItemBinding

class EventListViewHolder(private val binding: EventItemBinding, private val context: Context) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(event: SimplifiedEvent, onClick: (String) -> Unit) {
        with(binding) {
            eventTitle.text = event.title
            eventDate.text = event.date
            eventPrice.text = root.context.getString(R.string.event_price, event.price)
            eventCard.setOnClickListener { onClick(event.id) }
            setupImage(event)
        }
    }

    private fun setupImage(event: SimplifiedEvent) {
        Glide.with(context).load(event.image).centerCrop()
            .placeholder(R.drawable.ic_image_not_found).into(binding.eventImage)
    }

}