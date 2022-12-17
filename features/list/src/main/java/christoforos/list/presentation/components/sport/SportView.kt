package christoforos.list.presentation.components.sport

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import christoforos.list.databinding.LayoutSportViewBinding
import christoforos.list.domain.models.Event
import christoforos.list.presentation.components.event.EventListAdapter
import christoforos.ui.R

class SportView(
    context: Context,
    attrs: AttributeSet?
) : ConstraintLayout(context, attrs), SportViewInterface {

    private val binding: LayoutSportViewBinding

    private val eventListAdapter = EventListAdapter(::favoriteClicked)

    private var onFavoriteClicked: ((event: Event) -> Unit)? = null

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

    override fun fillList(events: List<Event>) {
        eventListAdapter.submitList(events)
    }

    override fun setOnFavoriteClicked(listener: (event: Event) -> Unit) {
        onFavoriteClicked = listener
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

    override fun setIcon(resId: Int?) {
        resId?.let {
            binding.icon.setImageResource(it)
        } ?: run {
            binding.icon.setImageResource(R.drawable.circle)
        }
    }

    private fun favoriteClicked(event: Event) {
        onFavoriteClicked?.invoke(event)
    }

}