package christoforos.common.presentation.components.event

import androidx.recyclerview.widget.DiffUtil
import christoforos.common.domain.models.event.Event


class EventsDiffCallback : DiffUtil.ItemCallback<Event>() {
    override fun areItemsTheSame(oldItem: Event, newItem: Event) = oldItem == newItem

    override fun areContentsTheSame(oldItem: Event, newItem: Event) = oldItem == newItem
}
