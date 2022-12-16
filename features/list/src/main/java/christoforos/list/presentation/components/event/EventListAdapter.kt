package christoforos.list.presentation.components.event

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import christoforos.list.domain.models.Event

class EventListAdapter(
    val onFavoriteClicked: (event: Event) -> Unit
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
            EventViewResolver(view as EventView).render(event) {
                onFavorite(event)
            }
        }

        private fun onFavorite(event : Event){
            val eventToUpdate = currentList.firstOrNull { it.id == event.id }
            eventToUpdate?.let {
                it.favorite = !it.favorite
            }

            val sortedEvents = currentList
                .sortedByDescending { it.timestamp }
                .sortedByDescending { it.favorite }

            val oldIndex = currentList.indexOf(event)
            submitList(sortedEvents)
            val newIndex = currentList.indexOf(event)

            notifyItemChanged(oldIndex)
            notifyItemChanged(newIndex)

            onFavoriteClicked(event)
        }
    }

}
