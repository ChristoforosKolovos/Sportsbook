package christoforos.list.presentation.components.sport

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import christoforos.common.domain.models.event.Event
import christoforos.list.domain.models.Sport

class SportListAdapter(
    val onFavoriteClicked: (event: Event) -> Unit
) : ListAdapter<Sport, SportListAdapter.ViewHolder>(SportDiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SportListAdapter.ViewHolder {
        val view = SportView(parent.context, null)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: SportListAdapter.ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemCount() = currentList.size

    inner class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(sport: Sport) {
            SportViewResolver(
                view as SportView,
                sport,
                onFavoriteClicked,
                ::onDataChanged
            ) {
                onExpand(sport)
            }
        }

        private fun onExpand(sport: Sport) {
            val indexOfUpdatedItem = currentList.indexOf(sport)
            with(currentList[indexOfUpdatedItem]) {
                collapsed = !collapsed
            }
            notifyItemChanged(indexOfUpdatedItem)
        }

        private fun onDataChanged(data: List<Event>) {
            val sportId = data.first().sportId
            val sport = currentList.singleOrNull { it.id == sportId }
            val sportIndex = currentList.indexOf(sport)
            if (sportIndex >= 0) currentList[sportIndex].events = data
        }
    }

}
