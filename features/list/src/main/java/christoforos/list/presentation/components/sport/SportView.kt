package christoforos.list.presentation.components.sport

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import christoforos.common.helpers.CustomSnapHelper
import christoforos.list.databinding.LayoutSportViewBinding
import christoforos.list.domain.models.Event
import christoforos.list.presentation.components.event.EventListAdapter

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

        val snapHelper = CustomSnapHelper()
        snapHelper.attachToRecyclerView(recyclerView)

        binding.title.setOnClickListener {
            val lp = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                0
            )
            binding.list.layoutParams = lp
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

    private fun favoriteClicked(event: Event) {
        onFavoriteClicked?.invoke(event)
    }

}