package christoforos.list.presentation.components.sport

import christoforos.common.domain.models.event.Event
import christoforos.common.utils.StringConstants.DOTS
import christoforos.list.domain.models.Sport
import christoforos.list.presentation.components.sport.EventIconUtils.sportIdToResourceId

class SportViewResolver(
    private val view: SportViewInterface,
    private val sport: Sport,
    onFavoriteClicked: (event: Event) -> Unit,
    onDataChanged: (data: List<Event>) -> Unit,
    onExpandClicked: () -> Unit
) {

    init {
        view.setOnFavoriteClicked(onFavoriteClicked)
        view.setOnDataChanged(onDataChanged)
        view.setOnExpandClicked(onExpandClicked)
        view.setIcon(sportIdToResourceId(sport.id))
        setTitle()
        fillList()
        setExpandedOrCollapsed()
    }

    private fun setTitle() {
        sport.description?.let {
            view.setTitle(it)
        } ?: kotlin.run {
            view.setTitle(DOTS)
        }
    }

    private fun fillList() {
        val events = sport.events
        if (events.isNullOrEmpty()) {
            view.showNoEvents()
        } else {
            view.setEvents(events)
        }

    }

    private fun setExpandedOrCollapsed() {
        if (sport.collapsed)
            view.collapse()
        else
            view.expand()
    }
}