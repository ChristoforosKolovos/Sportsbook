package christoforos.list.presentation.components.event

import androidx.recyclerview.widget.DiffUtil
import christoforos.list.domain.models.Event


class EventsDiffCallback : DiffUtil.ItemCallback<Event>() {
    override fun areItemsTheSame(oldItem: Event, newItem: Event) = oldItem == newItem

    override fun areContentsTheSame(oldItem: Event, newItem: Event) = oldItem == newItem
}
