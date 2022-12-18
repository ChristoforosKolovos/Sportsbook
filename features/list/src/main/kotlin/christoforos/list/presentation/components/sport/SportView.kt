package christoforos.list.presentation.components.sport

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import christoforos.common.domain.models.event.Event
import christoforos.list.databinding.LayoutSportViewBinding
import christoforos.list.presentation.components.event.EventListAdapter

class SportView(
    context: Context,
    attrs: AttributeSet?
) : ConstraintLayout(context, attrs), SportViewInterface {

    private val binding: LayoutSportViewBinding

    private val eventListAdapter = EventListAdapter(::favoriteClicked, ::dataChanged)

    private var onFavoriteClicked: ((event: Event) -> Unit)? = null
    private var onDataChanged: ((data: List<Event>) -> Unit)? = null

    init {
        binding = LayoutSportViewBinding.inflate(LayoutInflater.from(context), this, true)

        val lp = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        layoutParams = lp

        val recyclerView = binding.list
        with(recyclerView) {
            layoutManager =
                LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
            adapter = eventListAdapter
        }
    }

    override fun setTitle(title: String) {
        binding.title.text = title
    }

    override fun setEvents(events: List<Event>) {
        binding.noEvents.isVisible = false
        binding.list.isVisible = true

        eventListAdapter.submitList(events)
    }

    override fun showNoEvents() {
        binding.noEvents.isVisible = true
        binding.list.isVisible = false
    }


    override fun setOnFavoriteClicked(listener: (event: Event) -> Unit) {
        onFavoriteClicked = listener
    }

    override fun setOnDataChanged(listener: (data: List<Event>) -> Unit) {
        onDataChanged = listener
    }

    override fun setOnExpandClicked(action: () -> Unit) {
        binding.header.setOnClickListener {
            action()
        }
    }

    override fun expand() {
        binding.list.isVisible = true
        binding.arrow.rotation = 0f
    }

    override fun collapse() {
        binding.list.isVisible = false
        binding.arrow.rotation = 180f
    }

    override fun setIcon(resId: Int) {
        binding.icon.setImageResource(resId)
    }

    private fun favoriteClicked(event: Event) {
        onFavoriteClicked?.invoke(event)
    }

    private fun dataChanged(data: List<Event>) {
        onDataChanged?.invoke(data)
    }

}