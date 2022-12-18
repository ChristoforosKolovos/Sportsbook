package christoforos.common.presentation.components.event

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import christoforos.common.domain.models.event.Event

class EventListAdapter(
    val onFavoriteClicked: (event: Event) -> Unit,
    val matchParentWidth: Boolean = false,
    val sortDataOnFavorite: Boolean = false,
    val onDataChanged: (data: List<Event>) -> Unit = {},
    val onAllItemsRemoved: () -> Unit = {}
) : ListAdapter<Event, EventListAdapter.ViewHolder>(EventsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = EventView(parent.context, null)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemCount() = currentList.size

    fun remove(event: Event) {
        val eventIndex = currentList.indexOf(event)
        if (eventIndex >= 0) {
            val list = currentList.toMutableList()
            list.removeAt(eventIndex)
            submitList(list)

            if (list.isEmpty()) onAllItemsRemoved()
        }
    }

    inner class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(event: Event) {
            EventViewResolver(view as EventView, event, matchParentWidth) {
                onFavorite(event)
            }
        }

        private fun onFavorite(event: Event) {
            currentList.firstOrNull {
                it.id == event.id
            }?.let {
                it.favorite = !it.favorite
            }

            if (sortDataOnFavorite) {
                val sortedEvents = currentList
                    .sortedByDescending { it.timestamp }
                    .sortedByDescending { it.favorite }
                submitList(sortedEvents)
                onDataChanged(sortedEvents)
            }

            notifyItemChanged(currentList.indexOf(event))
            onFavoriteClicked(event)
        }
    }

}
