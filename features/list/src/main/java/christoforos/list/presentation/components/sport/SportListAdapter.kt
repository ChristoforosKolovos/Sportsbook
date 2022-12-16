package christoforos.list.presentation.components.sport

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import christoforos.list.domain.models.Event
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
            SportViewResolver(view as SportView).render(sport, onFavoriteClicked) {
                onExpand(sport)
            }
        }

        private fun onExpand(sport: Sport) {
            val indexOfUpdatedItem = currentList.indexOf(sport)
            with(currentList[indexOfUpdatedItem]){
                collapsed = !collapsed
            }
            notifyItemChanged(indexOfUpdatedItem)
        }
    }

}
