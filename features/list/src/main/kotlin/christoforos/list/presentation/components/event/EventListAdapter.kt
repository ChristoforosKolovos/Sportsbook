package christoforos.list.presentation.components.event

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import christoforos.common.domain.models.event.Event

class EventListAdapter(
    val onFavoriteClicked: (event: Event) -> Unit,
    val onDataChanged: (data: List<Event>) -> Unit
) : ListAdapter<Event, EventListAdapter.ViewHolder>(EventsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = EventView(parent.context, null)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemCount() = currentList.size

    inner class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(event: Event) {
            EventViewResolver(view as EventView, event) {
                onFavorite(event)
            }
        }

        private fun onFavorite(event: Event) {
            currentList.firstOrNull {
                it.id == event.id
            }?.let {
                it.favorite = !it.favorite
            }

            val sortedEvents = currentList
                .sortedByDescending { it.timestamp }
                .sortedByDescending { it.favorite }

            submitList(sortedEvents)

            notifyItemChanged(currentList.indexOf(event))

            onDataChanged(sortedEvents)

            onFavoriteClicked(event)
        }
    }

}
