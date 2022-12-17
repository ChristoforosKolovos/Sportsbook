package christoforos.list.presentation.components.sport

import christoforos.list.domain.models.Event
import christoforos.list.domain.models.Sport
import christoforos.list.presentation.components.sport.EventIconUtils.sportIdToResourceId

class SportViewResolver(
    private val view: SportViewInterface,
    private val sport: Sport,
    private val onFavoriteClicked: (event: Event) -> Unit,
    private val onExpandClicked: () -> Unit
) {

    init {
        setTitle()
        fillList()
        setOnFavoriteClicked()
        setOnExpandClicked()
        setExpandedOrCollapsed()
        setIcon()
    }

    private fun setTitle() {
        sport.description?.let { view.setTitle(it) }
    }

    private fun fillList() {
        sport.events?.let { view.fillList(it) }
    }

    private fun setOnFavoriteClicked() {
        view.setOnFavoriteClicked(onFavoriteClicked)
    }

    private fun setOnExpandClicked() {
        view.setOnExpandClicked(onExpandClicked)
    }

    private fun setExpandedOrCollapsed() {
        if (sport.collapsed) view.collapse() else view.expand()
    }

    private fun setIcon() {
        view.setIcon(sportIdToResourceId(sport.id))
    }
}