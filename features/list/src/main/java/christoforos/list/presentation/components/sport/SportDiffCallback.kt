package christoforos.list.presentation.components.sport

import androidx.recyclerview.widget.DiffUtil
import christoforos.list.domain.models.Sport


class SportDiffCallback : DiffUtil.ItemCallback<Sport>() {
    override fun areItemsTheSame(oldItem: Sport, newItem: Sport) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Sport, newItem: Sport) = oldItem == newItem
}
