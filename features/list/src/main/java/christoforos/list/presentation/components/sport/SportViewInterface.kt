package christoforos.list.presentation.components.sport

import christoforos.list.domain.models.Event

interface SportViewInterface {

    fun setTitle(title: String)

    fun setEvents(events: List<Event>)

    fun showNoEvents()

    fun setOnFavoriteClicked(listener: (event: Event) -> Unit)

    fun setOnExpandClicked(action: () -> Unit)

    fun expand()

    fun collapse()

    fun setIcon(resId: Int)
}